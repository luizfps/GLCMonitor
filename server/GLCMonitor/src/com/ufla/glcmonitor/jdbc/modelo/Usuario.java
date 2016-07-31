package com.ufla.glcmonitor.jdbc.modelo;

import java.util.Date;
import java.util.List;

public class Usuario {

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
    	if(sexo == null) {
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
        this.sensores = sensores;
    }

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", dataDeCadastramento="
				+ dataDeCadastramento + ", rg=" + rg + ", cpf=" + cpf + ", sexo=" + sexo + ", dataDeNascimento="
				+ dataDeNascimento + ", login=" + login + ", senha=" + senha + ", endereco=" + endereco + ", sensores="
				+ sensores + "]";
	}
	
	
}
