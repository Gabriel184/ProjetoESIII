select * from matricula as m1
inner join aluno as a1 on m1.alu_id = a1.id_alu
inner join endereco as e1 on a1.end_id = e1.id_end
inner join curso as c1 on m1.cur_id = c1.id_cur
inner join categoria as c2 on c1.cat_id = c2.id_cat;