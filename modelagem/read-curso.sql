select id_cur, c1.descricao as cur_descricao, duracao, cat_id, id_cat, c2.descricao as cat_descricao from curso as c1
inner join categoria as c2
on c1.cat_id = c2.id_cat
where c1.descricao = 'Biomedicina';