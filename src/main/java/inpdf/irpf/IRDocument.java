package inpdf.irpf;

import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.Map.entry;

import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IRDocument {
	
	@Expose
	public IRContribuinteID cid = new IRContribuinteID();
	@Expose
	public IRRendimentosTributaveisPJTitular rtpt = new IRRendimentosTributaveisPJTitular();
	@Expose
	public IRRendimentosIsentos ri = new IRRendimentosIsentos();
	@Expose
	public IRRendimentosExclusivos re = new IRRendimentosExclusivos();
	@Expose
	public IRRendimentosAcumuladosPJTitular rapjt = new IRRendimentosAcumuladosPJTitular();
	@Expose
	public IRImpostoPagoRetido iper = new IRImpostoPagoRetido();
	@Expose
	public IRPagamentosEfetuados pe = new IRPagamentosEfetuados();
	@Expose
	public IRBensDireitos bd = new IRBensDireitos();
	@Expose
	public IRDividasOnus irdo = new IRDividasOnus();
	@Expose
	public IRResumoDeclaracao rd = new IRResumoDeclaracao();
	
	public final LinkedHashMap<IRSectionsEnum, IRSection> sections = new LinkedHashMap<>();
		

	public IRDocument() {
		put();
	}
	
	public void refresh() {
		cid = (IRContribuinteID) sections.get(cid.getType());
		rtpt = (IRRendimentosTributaveisPJTitular) sections.get(rtpt.getType());
		ri = (IRRendimentosIsentos) sections.get(ri.getType());
		re = (IRRendimentosExclusivos) sections.get(re.getType());
		rapjt = (IRRendimentosAcumuladosPJTitular) sections.get(rapjt.getType());
		iper = (IRImpostoPagoRetido) sections.get(iper.getType());
		pe = (IRPagamentosEfetuados) sections.get(pe.getType());
		bd = (IRBensDireitos) sections.get(bd.getType());
		irdo = (IRDividasOnus) sections.get(irdo.getType());
		rd = (IRResumoDeclaracao) sections.get(rd.getType());
	}
	
	public void put() {
		sections.put(cid.getType(), cid);
		sections.put(rtpt.getType(), rtpt);
		sections.put(ri.getType(), ri);
		sections.put(re.getType(), re);
		sections.put(rapjt.getType(), rapjt);
		sections.put(iper.getType(), iper);
		sections.put(pe.getType(), pe);
		sections.put(bd.getType(), bd);
		sections.put(irdo.getType(), irdo);
		sections.put(rd.getType(), rd);
	}
}