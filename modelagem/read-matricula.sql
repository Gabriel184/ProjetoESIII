select
m1.id_mat, m1.alu_id, m1.cur_id, m1.codigo, m1.data_cadastro, m1.turma, 
a1.id_alu, a1.end_id, a1.nome, a1.cpf, a1.telefone, a1.data_nascimento, 
a1.email, e1.id_end, e1.cep, e1.logradouro, e1.cidade, e1.estado, e1.numero, 
e1.bairro, e1.complemento, e1.codigo AS end_codigo, c1.id_cur, c1.descricao, 
c1.duracao, c1.cat_id, c2.id_cat, c2.descricao AS cat_descricao
from matricula as m1
inner join aluno as a1 on m1.alu_id = a1.id_alu
inner join endereco as e1 on a1.end_id = e1.id_end
inner join curso as c1 on m1.cur_id = c1.id_cur
inner join categoria as c2 on c1.cat_id = c2.id_cat
where m1.codigo = '202157-direito penal e criminologia-m0';