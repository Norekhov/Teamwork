create linked table TRANSACTIONS('','jdbc:h2:file:./transaction','','', '(SELECT * FROM TRANSACTIONS)');
create linked table USERS('','jdbc:h2:file:./transaction','','', '(SELECT * FROM USERS)');
create linked table PRODUCTS('','jdbc:h2:file:./transaction','','', '(SELECT * FROM PRODUCTS)');
create linked table RECOMMENDATIONS('','jdbc:h2:file:./recommendations','','', '(SELECT * FROM RECOMMENDATIONS)');
create linked table RULES('','jdbc:h2:file:./recommendations','','', '(SELECT * FROM RULES)');
create linked table RULESETS('','jdbc:h2:file:./recommendations','','', '(SELECT * FROM RULESETS)');