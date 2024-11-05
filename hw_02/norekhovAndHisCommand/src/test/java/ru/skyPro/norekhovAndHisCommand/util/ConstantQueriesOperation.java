package ru.skyPro.norekhovAndHisCommand.util;

public class ConstantQueriesOperation {

    public static final String NAME500 = "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налогналоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости";
    public static final String SIMPLE_CREDIT_DESCRIPTION = "Простой кредит. Откройте мир выгодных кредитов с нами!Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.Почему выбирают нас: Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов. Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении. Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое. Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";
    public static final String TOP_SAVING_DESCRIPTION = "Простой кредит. Откройте мир выгодных кредитов с нами!Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.Почему выбирают нас: Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов. Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении. Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое. Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";

    public static final String[] SAVING_ARGS = {"DEBIT", ">=", "<=", "50000"};
    public static String[] DEBIT = {"DEBIT"};
    public static String[] CREDIT_ARGS = {"CREDIT"};
    public static String[] INVEST_ARGS = {"SAVING",">","10000"};
    public static String[] TRANSACTIONS_ARGS = {"DEPOSIT", "WITHDRAW", ">"};
    public static final String[] INVEST = {"INVEST"};

    public static final String TRANSACTION_SUM_COMPARE = "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";
    public static final String USER = "USER_OF";
    public static final String ACTIVE = "ACTIVE_USER_OF";
    public static final String TRANSACTION_SUM = "TRANSACTION_SUM_COMPARE";
    public static final String SAVING = "SAVING";
    public static final String CREDIT = "CREDIT";

    public static final String TRANSACTION_SUM_FOR_INVEST_QUERY = "SUM(CASE WHEN p.type = 'SAVING' AND t.amount > 0 THEN t.amount ELSE 0 END) > 1000";
    public static final String TRANSACTION_SUM_COMPARE_QUERY = " SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'WITHDRAW' THEN t.amount ELSE 0 END";
    public static final String USER_QUERY = "SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0";
    public static final String USER_FOR_INVEST_QUERY = "SUM(CASE WHEN p.type = 'INVEST' THEN 1 ELSE 0 END) = 0";
    public static final String USER_FOR_CREDIT_QUERY = "SUM(CASE WHEN p.type = 'CREDIT' THEN 1 ELSE 0 END) = 0 ";
    public static final String USER_FOR_SAVING = "(SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) >= 50000 OR SUM(CASE WHEN p.type = 'SAVING' THEN t.amount ELSE 0 END) >= 50000) ";

    public static final String INVEST500 = "Invest500";
    public static final String TOP_SAVING = "Top Saving";
    public static final String SIMPLE_CREDIT = "Simple Credit";
}
