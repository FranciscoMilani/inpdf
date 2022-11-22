package inpdf.irpf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;

public class IRContribuinteID extends IRSection {
	@Expose
	private final IRSectionsEnum type = IRSectionsEnum.IDENTIFICACAO_CONTRIBUINTE;
	@Expose
	private final List<IRField> fields = Arrays.asList(
			new IRField("Nome"),
			new IRField("Data de Nascimento"),
			new IRField("CPF"),
			new IRField("Título Eleitoral"),
			new IRField("Possui cônjuge ou companheiro(a)"),
			new IRField("CPF do cônjuge ou companheiro(a)"),
			new IRField("Endereço"),
			new IRField("Número"),
			new IRField("Complemento"),
			new IRField("Bairro/Distrito"),
			new IRField("Município"),
			new IRField("UF"),
			new IRField("CEP"),
			new IRField("DDD/Telefone"),
			new IRField("DDD/Celular"),
			new IRField("E-mail"),
			new IRField("Natureza da Ocupação"),
			new IRField("Ocupação Principal"),
			new IRField("Tipo de declaração"));

	
	@Override
	public IRSectionsEnum getType() {
		return type;
	}

	@Override
	public List<IRField> getFields() {
		return fields;
	}
}