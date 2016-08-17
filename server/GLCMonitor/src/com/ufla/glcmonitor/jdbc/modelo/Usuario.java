package com.ufla.glcmonitor.jdbc.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Usuario implements Comparable<Usuario> {

	private String nome;
	private Long telefone;
	private String email;
	private Date dataDeCadastramento;
	private Long rg;
	private Long cpf;
	private Sexo sexo;
	private Date dataDeNascimento;
	private String login;
	private String senha;
	private Endereco endereco;
	private List<Sensor> sensores;

	public Usuario() {
		sensores = new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataDeCadastramento() {
		return dataDeCadastramento;
	}

	public void setDataDeCadastramento(Date dataDeCadastramento) {
		this.dataDeCadastramento = dataDeCadastramento;
	}

	public Long getRg() {
		return rg;
	}

	public void setRg(Long rg) {
		this.rg = rg;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public String getSexoValueStr() {
		if (sexo == null) {
			return null;
		}
		return String.valueOf(sexo.getValue());
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public void setSexo(Character sexoValue) {
		this.sexo = Sexo.getSexo(sexoValue);
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Sensor> getSensores() {
		return sensores;
	}

	public void setSensores(List<Sensor> sensores) {
		if (sensores != null) {
			this.sensores = sensores;
		}
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Sensor getSensor(Long codigo) {
		Sensor sensor = new Sensor();
		sensor.setCodigo(codigo);
		Collections.sort(sensores);
		int indice = Collections.binarySearch(sensores, sensor);
		if (indice == -1) {
			return null;
		} else {
			return sensores.get(indice);
		}
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", telefone=" + telefone + ", email=" + email
				+ ", dataDeCadastramento=" + dataDeCadastramento + ", rg=" + rg + ", cpf=" + cpf
				+ ", sexo=" + sexo + ", dataDeNascimento=" + dataDeNascimento + ", login=" + login
				+ ", senha=" + senha + ", endereco=" + endereco + ", sensores=" + sensores + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((dataDeCadastramento == null) ? 0 : dataDeCadastramento.hashCode());
		result = prime * result + ((dataDeNascimento == null) ? 0 : dataDeNascimento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((sensores == null) ? 0 : sensores.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataDeCadastramento == null) {
			if (other.dataDeCadastramento != null)
				return false;
		} else if (!dataDeCadastramento.equals(other.dataDeCadastramento))
			return false;
		if (dataDeNascimento == null) {
			if (other.dataDeNascimento != null)
				return false;
		} else if (!dataDeNascimento.equals(other.dataDeNascimento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (sensores == null) {
			if (other.sensores != null)
				return false;
		} else if (!sensores.equals(other.sensores))
			return false;
		if (sexo != other.sexo)
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

	@Override
	public int compareTo(Usuario outroUsuario) {
		return this.login.compareTo(outroUsuario.login);
	}

}
