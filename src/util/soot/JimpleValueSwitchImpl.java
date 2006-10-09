/*
 * Created on Sep 29, 2005
 */
package util.soot;

import soot.Local;
import soot.jimple.*;

public abstract class JimpleValueSwitchImpl implements JimpleValueSwitch {
	public void caseLocal (Local l) {
		defaultCase (l);
	}

	public void caseDoubleConstant (DoubleConstant v) {
		defaultCase (v);
	}

	public void caseFloatConstant (FloatConstant v) {
		defaultCase (v);
	}

	public void caseIntConstant (IntConstant v) {
		defaultCase (v);
	}

	public void caseLongConstant (LongConstant v) {
		defaultCase (v);
	}

	public void caseNullConstant (NullConstant v) {
		defaultCase (v);
	}

	public void caseStringConstant (StringConstant v) {
		defaultCase (v);
	}

	public void caseClassConstant (ClassConstant v) {
		defaultCase (v);
	}

	public void defaultCase (@SuppressWarnings("unused")
	                         Object object) {
	}

	public void caseAddExpr (AddExpr v) {
		defaultCase (v);
	}

	public void caseAndExpr (AndExpr v) {
		defaultCase (v);
	}

	public void caseCmpExpr (CmpExpr v) {
		defaultCase (v);
	}

	public void caseCmpgExpr (CmpgExpr v) {
		defaultCase (v);
	}

	public void caseCmplExpr (CmplExpr v) {
		defaultCase (v);
	}

	public void caseDivExpr (DivExpr v) {
		defaultCase (v);
	}

	public void caseEqExpr (EqExpr v) {
		defaultCase (v);
	}

	public void caseNeExpr (NeExpr v) {
		defaultCase (v);
	}

	public void caseGeExpr (GeExpr v) {
		defaultCase (v);
	}

	public void caseGtExpr (GtExpr v) {
		defaultCase (v);
	}

	public void caseLeExpr (LeExpr v) {
		defaultCase (v);
	}

	public void caseLtExpr (LtExpr v) {
		defaultCase (v);
	}

	public void caseMulExpr (MulExpr v) {
		defaultCase (v);
	}

	public void caseOrExpr (OrExpr v) {
		defaultCase (v);
	}

	public void caseRemExpr (RemExpr v) {
		defaultCase (v);
	}

	public void caseShlExpr (ShlExpr v) {
		defaultCase (v);
	}

	public void caseShrExpr (ShrExpr v) {
		defaultCase (v);
	}

	public void caseUshrExpr (UshrExpr v) {
		defaultCase (v);
	}

	public void caseSubExpr (SubExpr v) {
		defaultCase (v);
	}

	public void caseXorExpr (XorExpr v) {
		defaultCase (v);
	}

	public void caseInterfaceInvokeExpr (InterfaceInvokeExpr v) {
		defaultCase (v);
	}

	public void caseSpecialInvokeExpr (SpecialInvokeExpr v) {
		defaultCase (v);
	}

	public void caseStaticInvokeExpr (StaticInvokeExpr v) {
		defaultCase (v);
	}

	public void caseVirtualInvokeExpr (VirtualInvokeExpr v) {
		defaultCase (v);
	}

	public void caseCastExpr (CastExpr v) {
		defaultCase (v);
	}

	public void caseInstanceOfExpr (InstanceOfExpr v) {
		defaultCase (v);
	}

	public void caseNewArrayExpr (NewArrayExpr v) {
		defaultCase (v);
	}

	public void caseNewMultiArrayExpr (NewMultiArrayExpr v) {
		defaultCase (v);
	}

	public void caseNewExpr (NewExpr v) {
		defaultCase (v);
	}

	public void caseLengthExpr (LengthExpr v) {
		defaultCase (v);
	}

	public void caseNegExpr (NegExpr v) {
		defaultCase (v);
	}

	public void caseArrayRef (ArrayRef v) {
		defaultCase (v);
	}

	public void caseStaticFieldRef (StaticFieldRef v) {
		defaultCase (v);
	}

	public void caseInstanceFieldRef (InstanceFieldRef v) {
		defaultCase (v);
	}

	public void caseParameterRef (ParameterRef v) {
		defaultCase (v);
	}

	public void caseCaughtExceptionRef (CaughtExceptionRef v) {
		defaultCase (v);
	}

	public void caseThisRef (ThisRef v) {
		defaultCase (v);
	}

}
