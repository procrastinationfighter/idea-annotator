package com.github.procrastinationfighter.ideaannotator;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiComment;
import org.jetbrains.annotations.NotNull;

public class KotlinCommentAnnotator extends CommentAnnotator {
    @Override
    protected void annotateComment(@NotNull PsiComment comment, @NotNull AnnotationHolder holder) {
        // Highlight any Kotlin comment.
        holder.newAnnotation(HighlightSeverity.INFORMATION, "Comment found!")
                .range(comment.getTextRange())
                .highlightType(ProblemHighlightType.INFORMATION)
                .create();
    }
}
