ALTER TABLE avaliacao_produto ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE categoria_produto ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE conta_pagar ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE conta_receber ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE cupom_desconto ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE endereco ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE forma_pagamento ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE imagem_produto ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE item_venda_loja ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE marca_produto ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE nota_fiscal_compra ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE nota_fiscal_venda ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE nota_item_produto ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE pessoa_fisica ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE pessoa_juridica ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE produto ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
      
ALTER TABLE usuario ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
      
ALTER TABLE usuario ADD CONSTRAINT login_unique UNIQUE (login);
      
ALTER TABLE status_rastreio ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);
	  
ALTER TABLE venda_compra_loja_virtual ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id)
      REFERENCES pessoa_juridica (id);