INSERT INTO app.user (id, username, email, phone, password, role, register_date, birth_date, name, surname)
VALUES (1,
        'User1',
        'UserToUpdate@test.by',
        '+375440000000',
        'pass',
        'administrator',
        '2000-01-01',
        '2000-01-01',
        'Вася',
        'Пупкин'),
       (2,
        'User2',
        'UserToDelete@test.by',
        '+375440000000',
        'pass',
        'administrator',
        '2000-01-01',
        '2000-01-01',
        'Петя',
        'Иванов');

ALTER SEQUENCE app.user_id_seq RESTART WITH 3;

INSERT INTO app.content_type (id, name, extra_field_types)
VALUES (1, 'news', NULL),
       (2, 'pages', NULL);

ALTER SEQUENCE app.content_type_id_seq RESTART WITH 3;

INSERT INTO app.category (id, parent_id, alias, created, active)
VALUES (1, DEFAULT, 'category1', '2019-01-01', TRUE),
       (2, DEFAULT, 'category2', '2019-01-01', TRUE);

ALTER SEQUENCE app.category_id_seq RESTART WITH 3;

INSERT INTO app.category_translate (category_id, lang, name, intro_text, meta_title, meta_description, meta_keywords)
VALUES (1,
        'RU',
        'Категория 1',
        'Вводный текст для категории 1',
        'Мета тэг Title для Категории 1',
        'Мета тэг description для Категории 1',
        'Мета тэг keyword для категории 1'),
       (1,
        'EN',
        'Category 1',
        'Intro text for category 1',
        'meta teg Title for category 1',
        'meta teg description for category 1',
        'meta teg keyword for category 1'),
       (2,
        'RU',
        'Категория 2',
        'Вводный текст для категории 2',
        'Мета тэг Title для Категории 2',
        'Мета тэг description для Категории 2',
        'Мета тэг keyword для категории 2'),
       (2,
        'EN',
        'Category 2',
        'Intro text for category 2',
        'meta teg Title for category 2',
        'meta teg description for category 2',
        'meta teg keyword for category 2');

INSERT INTO app.content (id,
                         content_type_id,
                         category_id,
                         alias,
                         created,
                         created_by,
                         last_modified,
                         publish_up,
                         publish_down,
                         hits,
                         extra_fields,
                         image)
VALUES (1, 1, 1, 'test1', '2019-01-01', 1, NULL, NULL, NULL, 1, NULL, NULL),
       (2, 2, 2, 'test2', '2019-01-01', 1, NULL, NULL, NULL, 1, NULL, NULL);

ALTER SEQUENCE app.content_id_seq RESTART WITH 3;

INSERT INTO app.content_translate (content_id,
                                   lang,
                                   name,
                                   intro_text,
                                   full_text,
                                   meta_title,
                                   meta_description,
                                   meta_keywords)
VALUES (1,
        'RU',
        'Тестовая новость 1',
        'Таким образом консультация с широким активом представляет собой интересный эксперимент проверки форм развития. Разнообразный и богатый опыт постоянное информационно-пропагандистское обеспечение нашей деятельности влечет за собой процесс внедрения и модернизации соответствующий условий активизации.',
        'Идейные соображения высшего порядка, а также рамки и место обучения кадров позволяет выполнять важные задания по разработке системы обучения кадров, соответствует насущным потребностям. Повседневная практика показывает, что рамки и место обучения кадров требуют от нас анализа направлений прогрессивного развития. Таким образом консультация с широким активом требуют от нас анализа новых предложений. С другой стороны консультация с широким активом влечет за собой процесс внедрения и модернизации форм развития. Идейные соображения высшего порядка, а также постоянный количественный рост и сфера нашей активности влечет за собой процесс внедрения и модернизации новых предложений. Равным образом постоянный количественный рост и сфера нашей активности обеспечивает широкому кругу (специалистов) участие в формировании систем массового участия. Товарищи! постоянное информационно-пропагандистское обеспечение нашей деятельности способствует подготовки и реализации позиций, занимаемых участниками в отношении поставленных задач. Повседневная практика показывает, что сложившаяся структура организации позволяет выполнять важные задания по разработке существенных финансовых и административных условий. Значимость этих проблем настолько очевидна, что реализация намеченных плановых заданий способствует подготовки и реализации направлений прогрессивного развития. Товарищи! укрепление и развитие структуры позволяет оценить значение форм развития.',
        'мета тэг Title новости 1',
        'мета тэг description новости 1',
        'мета тэг keyword новости 1'),
       (1,
        'EN',
        'Test news 1',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum euismod felis eget dignissim malesuada. Fusce mollis ipsum at est posuere, ut luctus ipsum faucibus. In accumsan eros sapien, a porta leo finibus in. Nunc ipsum tellus, vulputate ac volutpat vel, fermentum non orci. Pellentesque accumsan consectetur vestibulum. Interdum et malesuada fames ac ante ipsum primis in faucibus. ',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed porttitor tempus accumsan. Integer varius fringilla neque in efficitur. Duis massa dui, pretium quis magna sed, scelerisque bibendum dolor. Praesent in suscipit magna, at laoreet odio. Cras urna dui, placerat ac est vel, laoreet luctus dolor. Etiam tristique quis est in mattis. Sed auctor dui congue urna consequat, in tempor nisi gravida. Aliquam vitae interdum odio, sit amet sodales ante. Etiam non pulvinar dolor. Fusce in quam sed nunc efficitur congue sit amet eget leo. Curabitur cursus fermentum leo vel tempor. Cras ornare imperdiet odio, ac malesuada dolor faucibus eget. Aenean vehicula est est, euismod congue augue dictum id. Aliquam dapibus, nunc eget lobortis egestas, leo nibh dapibus sem, eu commodo massa quam eget nunc. Fusce vitae massa neque. Ut luctus finibus augue, ac mollis nunc commodo quis. Proin non orci ac turpis dapibus rutrum. Maecenas pellentesque quam urna, vitae pellentesque lectus gravida id. Donec quis leo placerat, ultricies mauris ut, feugiat ipsum. Nam rhoncus arcu et ex consequat tempor. Phasellus lectus nulla, bibendum ac justo nec, consectetur euismod ante. Ut posuere neque quis lectus sodales, eget dictum nulla ornare. Praesent lacinia eget nisl eget pulvinar. Nullam porttitor vel dolor at malesuada. Pellentesque consequat, tellus non finibus congue, dui dolor eleifend tortor, eget aliquam risus augue id leo. Curabitur turpis diam, sagittis et lacinia eget, mollis a purus. Integer ex felis, ultricies ut arcu ac, tempus euismod velit. Phasellus auctor leo ut faucibus ultrices. Suspendisse dignissim nisi neque, in convallis enim tempus quis. Ut euismod pellentesque tellus a ullamcorper. Vivamus sed velit quam. Phasellus lobortis semper felis, in ultricies dui tempus eu. Vivamus ullamcorper nulla vel velit accumsan, eu facilisis massa tempus. Praesent ut erat porttitor, eleifend dolor at, dignissim elit. Interdum et malesuada fames ac ante ipsum primis in faucibus.',
        'meta teg Title for news 1',
        'meta teg description for news 1',
        'meta teg keywords for news 1'),
       (2,
        'RU',
        'Тестовая новость 2',
        'Таким образом консультация с широким активом представляет собой интересный эксперимент проверки форм развития. Разнообразный и богатый опыт постоянное информационно-пропагандистское обеспечение нашей деятельности влечет за собой процесс внедрения и модернизации соответствующий условий активизации.',
        'Идейные соображения высшего порядка, а также рамки и место обучения кадров позволяет выполнять важные задания по разработке системы обучения кадров, соответствует насущным потребностям. Повседневная практика показывает, что рамки и место обучения кадров требуют от нас анализа направлений прогрессивного развития. Таким образом консультация с широким активом требуют от нас анализа новых предложений. С другой стороны консультация с широким активом влечет за собой процесс внедрения и модернизации форм развития. Идейные соображения высшего порядка, а также постоянный количественный рост и сфера нашей активности влечет за собой процесс внедрения и модернизации новых предложений. Равным образом постоянный количественный рост и сфера нашей активности обеспечивает широкому кругу (специалистов) участие в формировании систем массового участия. Товарищи! постоянное информационно-пропагандистское обеспечение нашей деятельности способствует подготовки и реализации позиций, занимаемых участниками в отношении поставленных задач. Повседневная практика показывает, что сложившаяся структура организации позволяет выполнять важные задания по разработке существенных финансовых и административных условий. Значимость этих проблем настолько очевидна, что реализация намеченных плановых заданий способствует подготовки и реализации направлений прогрессивного развития. Товарищи! укрепление и развитие структуры позволяет оценить значение форм развития.',
        'мета тэг Title новости 2',
        'мета тэг description новости 2',
        'мета тэг keyword новости 2'),
       (2,
        'EN',
        'Test news 2',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum euismod felis eget dignissim malesuada. Fusce mollis ipsum at est posuere, ut luctus ipsum faucibus. In accumsan eros sapien, a porta leo finibus in. Nunc ipsum tellus, vulputate ac volutpat vel, fermentum non orci. Pellentesque accumsan consectetur vestibulum. Interdum et malesuada fames ac ante ipsum primis in faucibus. ',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed porttitor tempus accumsan. Integer varius fringilla neque in efficitur. Duis massa dui, pretium quis magna sed, scelerisque bibendum dolor. Praesent in suscipit magna, at laoreet odio. Cras urna dui, placerat ac est vel, laoreet luctus dolor. Etiam tristique quis est in mattis. Sed auctor dui congue urna consequat, in tempor nisi gravida. Aliquam vitae interdum odio, sit amet sodales ante. Etiam non pulvinar dolor. Fusce in quam sed nunc efficitur congue sit amet eget leo. Curabitur cursus fermentum leo vel tempor. Cras ornare imperdiet odio, ac malesuada dolor faucibus eget. Aenean vehicula est est, euismod congue augue dictum id. Aliquam dapibus, nunc eget lobortis egestas, leo nibh dapibus sem, eu commodo massa quam eget nunc. Fusce vitae massa neque. Ut luctus finibus augue, ac mollis nunc commodo quis. Proin non orci ac turpis dapibus rutrum. Maecenas pellentesque quam urna, vitae pellentesque lectus gravida id. Donec quis leo placerat, ultricies mauris ut, feugiat ipsum. Nam rhoncus arcu et ex consequat tempor. Phasellus lectus nulla, bibendum ac justo nec, consectetur euismod ante. Ut posuere neque quis lectus sodales, eget dictum nulla ornare. Praesent lacinia eget nisl eget pulvinar. Nullam porttitor vel dolor at malesuada. Pellentesque consequat, tellus non finibus congue, dui dolor eleifend tortor, eget aliquam risus augue id leo. Curabitur turpis diam, sagittis et lacinia eget, mollis a purus. Integer ex felis, ultricies ut arcu ac, tempus euismod velit. Phasellus auctor leo ut faucibus ultrices. Suspendisse dignissim nisi neque, in convallis enim tempus quis. Ut euismod pellentesque tellus a ullamcorper. Vivamus sed velit quam. Phasellus lobortis semper felis, in ultricies dui tempus eu. Vivamus ullamcorper nulla vel velit accumsan, eu facilisis massa tempus. Praesent ut erat porttitor, eleifend dolor at, dignissim elit. Interdum et malesuada fames ac ante ipsum primis in faucibus.',
        'meta teg Title for news 2',
        'meta teg description for news 2',
        'meta teg keywords for news 2');

INSERT INTO app.tag (id, lang, value)
VALUES (1, 'RU', 'тэгДляОбновления'),
       (2, 'RU', 'тэгДляУдаления'),
       (3, 'EN', 'tag1'),
       (4, 'EN', 'teg2');

ALTER SEQUENCE app.tag_id_seq RESTART WITH 5;

INSERT INTO app.content_tag (content_id, tags_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (1, 3),
       (1, 4),
       (2, 3),
       (2, 4);

INSERT INTO app.comment (id, content_id, user_id, created, full_text, status)
VALUES (1, 1, 1, '2019-01-01 04:05:06', 'Комментарий1', 'PUBLISHED'),
       (2, 2, 1, '2019-01-01 04:05:06', 'Комментарий2', 'PUBLISHED'),
       (3, 1, 1, '2019-01-01 04:05:06', 'Комментарий3', 'PUBLISHED'),
       (4, 2, 1, '12019-01-01 04:05:06', 'Комментарий4', 'PUBLISHED');

ALTER SEQUENCE app.comment_id_seq RESTART WITH 5;