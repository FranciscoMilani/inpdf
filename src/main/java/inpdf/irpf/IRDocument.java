package inpdf.irpf;

import java.util.LinkedHashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IRDocument {
	
	@Expose
	@SerializedName(value = "identificacao_contribuinte")
	IRContribuinteID cid = new IRContribuinteID();
	
	@Expose
	@SerializedName(value = "rendimentos_tributaveis_recebidos_pj")
	IRRendimentosTributaveisPJTitular rtpt = new IRRendimentosTributaveisPJTitular();
	
//	IRImpostoPagoERetido iper = new IRImpostoPagoERetido();
//	IRPagamentosEfetuados pe = new IRPagamentosEfetuados();
//	IRResumoDeclaracao rd = new IRResumoDeclaracao();
	
	public final LinkedHashMap<IRSectionsEnum, IRSection> sections = new LinkedHashMap<IRSectionsEnum, IRSection>();

	
	public IRDocument() {
		sections.put(cid.type, cid);
		sections.put(rtpt.type, rtpt);
//		sections.put(iper.TYPE, iper);
//		sections.put(pe.TYPE, pe);
//		sections.put(rd.TYPE, rd);
	}
	
}