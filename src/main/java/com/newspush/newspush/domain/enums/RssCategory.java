package com.newspush.newspush.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RssCategory {
    정치("https://www.yna.co.kr/rss/politics.xml"),
    북한("https://www.yna.co.kr/rss/northkorea.xml"),
    경제("https://www.yna.co.kr/rss/economy.xml"),
    산업("https://www.yna.co.kr/rss/industry.xml"),
    사회("https://www.yna.co.kr/rss/society.xml");

    private final String url;
}