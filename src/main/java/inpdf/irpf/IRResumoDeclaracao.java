package inpdf.irpf;

import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;

public class IRResumoDeclaracao extends IRSection {
	@Expose
	private final IRSectionsEnum type = IRSectionsEnum.RESUMO_DECLARACAO;	
	@Expose
	private final List<IRField> fields = Arrays.asList(
			new IRField("Rendimentos Tributáveis"),
			new IRField("Deduções"),
			new IRField("Imposto devido"),
			new IRField("Imposto pago"),
			new IRField("Imposto a pagar"),
			new IRField("Imposto a restituir"),
			new IRField("Saldo de imposto a pagar"),
			
			new IRField("Valor da quota"),
			new IRField("Número de quotas"),
			
			new IRField("Débito automático"),
			new IRField("Banco"),
			new IRField("Agência (sem DV)"),
			new IRField("Conta para débito"),
			
			new IRField("Bens e direitos em 31/12/2020"),
			new IRField("Bens e direitos em 31/12/2021"),
			new IRField("Dívidas e ônus reais em 31/12/2020"),
			new IRField("Dívidas e ônus reais em 31/12/2021"),
			
			new IRField("Rendimentos isentos e não tributáveis"),
			new IRField("Rendimentos sujeitos à tributação exclusiva/definitiva"),
			new IRField("Rendimentos tributáveis - imposto com exigibilidade suspensa"),
			new IRField("Depósitos judiciais do imposto"),
			new IRField("Imposto pago sobre Ganhos de Capital"),
			new IRField("Imposto pago Ganhos de Capital Moeda Estrangeira - Bens, direitos e Aplicações Financeiras"),
			new IRField("Total do imposto retido na fonte (Lei n°11.033/2004), conforme dados informados pelo contribuinte"),
			new IRField("Imposto pago sobre Renda Variável"),
			new IRField("Doações a Partidos Políticos e Candidatos a Cargos Eletivos"),
			new IRField("Imposto a pagar sobre o Ganho de Capital - Moeda Estrangeira em Espécie"),
			new IRField("Imposto diferido dos Ganhos de Capital"),
			new IRField("Imposto devido sobre Ganhos de Capital"),
			new IRField("Imposto devido sobre ganhos liquidos em Renda Variável"),
			new IRField("Imposto devido sobre Ganhos de Capital Moeda Estrangeira - Bens, direitos e aplic, financeiras")
			);
	
	
	public IRResumoDeclaracao() {

	}
	
	@Override
	public IRSectionsEnum getType() {
		return type;
	}

	@Override
	public List<IRField> getFields() {
		return fields;
	}
}