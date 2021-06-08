SELECT COUNT(*) FROM matricula AS m1
INNER JOIN curso AS c1 ON m1.cur_id = c1.id_cur
WHERE m1.data_cadastro >= '2020-01-01' AND m1.data_cadastro <= '2020-06-30'
AND c1.descricao = 'Biomedicina';