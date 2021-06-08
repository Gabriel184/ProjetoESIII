SELECT CASE
	WHEN EXISTS (
		SELECT id_alu FROM aluno
		WHERE cpf = '123.456.789-90'
	)
	THEN true
	ELSE false
END