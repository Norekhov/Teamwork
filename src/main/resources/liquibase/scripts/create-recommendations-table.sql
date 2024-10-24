-- liquibase formatted sql

-- changeset exever:1
create sequence rules_seq start with 1 increment by 1;
create sequence recommendations_seq start with 1 increment by 1;
create table recommendations (
        id bigint default next value for recommendations_seq primary key,
        product_id uuid,
        product_name varchar(255),
        product_text varchar(1023)
    );
create table rules (
        id bigint default next value for rules_seq primary key,
        query tinyint check (query between 0 and 3),
        arguments varchar(255) array,
        negate boolean not null,
        alternative_rule_id bigint,
        constraint rules_id foreign key (alternative_rule_id) references rules(id)
    );
create table recommendations_rules (
        rules_id bigint not null,
        recommendation_id bigint not null,
        constraint recommendation_rule_rule_id foreign key (rules_id) references rules,
        constraint recommendation_rule_recommendation_id foreign key (recommendation_id) references recommendations
    );

INSERT INTO RECOMMENDATIONS (product_name, product_id, product_text) VALUES ('Invest 500','147f6a0f-3b91-413b-ab99-87f081d60d5a','Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!'),
('Top Saving','59efc529-2fff-41af-baff-90ccd7402925','Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
Преимущества «Копилки»:
Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.
Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.
Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.
Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!'),
('Простой кредит','ab138afb-f3ba-4a93-b74f-0fcee86d447f','Откройте мир выгодных кредитов с нами!
Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.
Почему выбирают нас:
Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.
Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.
Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.
Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!');

insert into RULES (query, arguments, negate, alternative_rule_id) values
(0, ARRAY ['CREDIT'], TRUE, null),
(3, ARRAY ['DEBIT', '>'], FALSE, null),
(2, ARRAY ['DEBIT', 'DEPOSIT', '>', '100000'], FALSE, null),

(0, ARRAY ['DEBIT'], FALSE, null),
(2, ARRAY ['DEBIT', 'DEPOSIT', '>=', '50000'], FALSE, null),
(2, ARRAY ['SAVING', 'DEPOSIT', '>=', '50000'], FALSE, 5),

(0, ARRAY ['INVEST'], TRUE, null),
(2, ARRAY ['SAVING', 'DEPOSIT', '>=', '1000'], FALSE, null);



insert into recommendations_rules (recommendation_id, rules_id) values
(3,1),(3,2),(3,3),
(2,4),(2,6),(2,2),
(1,4),(1,7),(1,8);