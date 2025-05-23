package br.com.postech.techchallange.adapter.out.persistence.entity.admin;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "admin_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminUserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_admin")
	private Long idAdmin;

	private String nome;

	private String email;

	@Column(name = "senha_hash")
	private String senhaHash;

	private Boolean ativo;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

}
