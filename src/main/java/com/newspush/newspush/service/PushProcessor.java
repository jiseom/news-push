package com.newspush.newspush.service;

import com.newspush.newspush.domain.entity.Article;

import java.util.List;

public interface PushProcessor {
    void process(List<Article> articles);
}
