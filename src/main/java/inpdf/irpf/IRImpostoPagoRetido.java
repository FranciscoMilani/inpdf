package inpdf.irpf;

import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;

public class IRImpostoPagoRetido extends IRSection {
	@Expose
	private final IRSectionsEnum type = IRSectionsEnum.IMPOSTO_PAGO_RETIDO;	
	@Expose
	private final List<IRField> fields = Arrays.asList(
			new IRField("Imposto complementar"),
			new IRField("Imposto pago no exterior pelo titular e pelos dependentes"),
			new IRField("Imposto devido com os rendimentos no exterior"),
			new IRField("Imposto devido sem os rendimentos no exterior"),
			new IRField("Diferença a ser considerada para cálculo do imposto (limite legal)"),
			new IRField("Imposto sobre a renda na fonte (Lei 11.033/2004)"),
			new IRField("Imposto retido na fonte do titular"),
			new IRField("Imposto retido na fonte dos dependentes"),
			new IRField("Carnê-Leão do titular"),
			new IRField("Carnê-Leão dos dependentes"));
	
	
	public IRImpostoPagoRetido() {

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