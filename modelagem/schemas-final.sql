CREATE TABLE endereco (
	id_end serial NOT NULL,
	cep character varying(255),
	logradouro character varying(255),
	cidade character varying(255),
	estado character varying(255),
	numero integer,
	bairro character varying(255),
	CONSTRAINT endereco_id_end PRIMARY KEY (id_end),
	CONSTRAINT endereco_cep_unique UNIQUE (cep)
);

CREATE TABLE curso (
	id_cur serial NOT NULL,
	descricao character varying(255),
	duracao integer,
	cat_id integer NOT NULL,
	CONSTRAINT curso_id_cur PRIMARY KEY (id_cur),
	CONSTRAINT cat_id_fk FOREIGN KEY (cat_id)
		REFERENCES categoria (id_cat)
);

CREATE TABLE aluno (
	id_alu serial NOT NULL,
	end_id integer NOT NULL,
	nome character varying(255),
	cpf character varying(255),
	telefone character varying(255),
	data_nascimento date,
	email character varying(255), 
	CONSTRAINT aluno_id_alu PRIMARY KEY (id_alu),
	CONSTRAINT end_id_pk FOREIGN KEY (end_id)
		REFERENCES endereco (id_end),
	CONSTRAINT aluno_cpf_unique UNIQUE (cpf)
);

CREATE TABLE categoria (
	id_cat serial NOT NULL,
	descricao character varying(255),
	CONSTRAINT categoria_id_cat PRIMARY KEY (id_cat) 
	CONSTRAINT categoria_unique UNIQUE (descricao)
);

CREATE TABLE matricula (
	id_mat serial NOT NULL,
	alu_id integer NOT NULL,
	cur_id integer NOT NULL,
	cat_id integer NOT NULL,
	codigo character varying(255),
	data_cadastro date,
	turma character varying(255),
	CONSTRAINT matricula_id_mat PRIMARY KEY (id_mat),
	CONSTRAINT matricula_alu_id_fk FOREIGN KEY (alu_id)
		REFERENCES aluno (id_alu),
	CONSTRAINT matricula_cur_id_fk FOREIGN KEY (cur_id)
		REFERENCES curso (id_cur),
	CONSTRAINT matricula_cat_id_fk FOREIGN KEY (cat_id)
		REFERENCES categoria (id_cat),
	CONSTRAINT matricula_codigo_unique UNIQUE (codigo)
);
