package inpdf.irpf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IRContribuinteID extends IRSection {
	public final IRSectionsEnum type = IRSectionsEnum.IDENTIFICACAO_CONTRIBUINTE;	
	private final List<String> fieldNames = Arrays.asList(
			"Nome",
			"Data de Nascimento",
			"CPF",
			"Título Eleitoral",
			"Possui cônjuge ou companheiro(a)",
			"CPF do cônjuge ou companheiro(a)",
			"Endereço",
			"Número",
			"Complemento",
			"Bairro/Distrito",
			"Município",
			"UF",
			"CEP",
			"DDD/Telefone",
			"DDD/Celular",
			"E-mail",
			"Natureza da Ocupação",
			"Ocupação Principal",
			"Tipo de declaração");
	
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
	public List<IRField> getFields() {
		return fields;
	}

	@Override
	public List<String> getFieldNames() {
		return fieldNames;
	}

	@Override
	public void setFieldsValues(List<IRField> newFields) {
		super.setFieldsValues(newFields);
	}

	@Override
	public IRSectionsEnum getType() {
		return type;
	}
}