package com.github.procrastinationfighter.ideaannotator;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaDependencyAnnotator extends CommentAnnotator {

    // Compiling patterns many times may result expensive.
    private static final Pattern pattern = Pattern.compile("[a-zA-Z0-9\\-.]+");

    protected void annotateComment(@NotNull final PsiComment comment, @NotNull AnnotationHolder holder) {
        // Documentation says that we should NOT store any state in the annotator,
        // so we can't keep it as a member variable, because dependencies can change over time.
        VirtualFile file = comment.getContainingFile().getVirtualFile();
        @Nullable Module module = ProjectRootManager
                .getInstance(comment.getProject())
                .getFileIndex()
                .getModuleForFile(file);
        if (module == null) {
            System.err.println("Module not found.");
            return;
        }
        OrderEnumerator enumerator = OrderEnumerator.orderEntries(module);

        var dependenciesNames = Arrays
                .stream(enumerator.getClassesRoots())
                .map(VirtualFile::getName)
                .collect(Collectors.toSet());

        int basicOffset = comment.getTextOffset();

        // Find the dependencies in comment.
        Matcher m = pattern.matcher(comment.getText());

        while (m.find()) {
            String word = m.group();
            if (dependenciesNames.contains(word)) {
                holder.newAnnotation(HighlightSeverity.INFORMATION, word + " is a dependency.")
                        .range(TextRange.from(basicOffset + m.start(), word.length()))
                        .highlightType(ProblemHighlightType.INFORMATION)
                        .create();
            }
        }
    }

}