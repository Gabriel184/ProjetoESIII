SELECT CASE
	WHEN EXISTS (
		SELECT id_end FROM endereco
		WHERE codigo = '08737010531'
	)
	THEN true
	ELSE false
END