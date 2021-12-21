package com.github.procrastinationfighter.ideaannotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;


public abstract class CommentAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        // Ensure the Psi Element is a comment.
        if (!(element instanceof PsiComment)) {
            return;
        }

        PsiComment com = (PsiComment) element;
        annotateComment(com, holder);
    }

    protected abstract void annotateComment(@NotNull final PsiComment comment, @NotNull AnnotationHolder holder);
}
