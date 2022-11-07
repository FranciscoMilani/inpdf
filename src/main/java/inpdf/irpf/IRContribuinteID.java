package inpdf.irpf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IRContribuinteID {
	public final String TYPE = "Identificação do Contribuinte";
	private List<IRField> fields;

	private void addFields() {
		fields = Arrays.asList(
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
				new IRField("Tipo de declaração")
				);
	}
}