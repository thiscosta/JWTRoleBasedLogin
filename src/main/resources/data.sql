insert into permissao (id, nome) values 
	(1, 'ROLE_ADMIN'), 
	(2, 'ROLE_USER')
;
	
insert into usuario (id, nome, login, senha, ativo) values 
	(1, 'Administrador', 'admin', '123', true), 
	(2, 'Usuario', 'user', '123', true)
;
	
insert into usuario_permissao (usuario_id, permissao_id) values 
	(1, 1), 
	(2, 2)
;


insert into grupo (id, nome) values 
	(1, 'ADMINISTRADOR'), 
	(2, 'USUARIO')
;
	
insert into usuario_grupo (usuario_id, grupo_id) values 
	(1, 1), 
	(2, 2);
	
insert into grupo_permissao (grupo_id, permissao_id) values 
	(1, 1), 
	(2, 2)
;