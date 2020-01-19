// $ANTLR null /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g 2020-01-19 10:23:38

    package om.frankc.csc435.compiler.generated;

    import om.frankc.csc435.compiler.ast.*;

	import java.util.*;

    // This must be manually imported as it conflicts
    // with the java.util.BitSet class.
	import org.antlr.runtime.BitSet;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.debug.*;
import java.io.IOException;
@SuppressWarnings("all")
public class Csc435Parser extends DebugParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADD_OP", "BOOLEAN", "BOOLEAN_CONSTANT", 
		"CHAR", "CHAR_CONSTANT", "CLOSE_BRACE", "CLOSE_BRACKET", "CLOSE_PAREN", 
		"COMMA", "COMMENT", "ELSE", "EQUALS", "EQUAL_OP", "FLOAT", "FLOAT_CONSTANT", 
		"ID", "IF", "INT", "INT_CONSTANT", "LESS_OP", "MULT_OP", "OPEN_BRACE", 
		"OPEN_BRACKET", "OPEN_PAREN", "PRINT", "PRINTLN", "RETURN", "SEMICOLON", 
		"STRING", "STRING_CONSTANT", "SUB_OP", "VOID", "WHILE", "WS"
	};
	public static final int EOF=-1;
	public static final int ADD_OP=4;
	public static final int BOOLEAN=5;
	public static final int BOOLEAN_CONSTANT=6;
	public static final int CHAR=7;
	public static final int CHAR_CONSTANT=8;
	public static final int CLOSE_BRACE=9;
	public static final int CLOSE_BRACKET=10;
	public static final int CLOSE_PAREN=11;
	public static final int COMMA=12;
	public static final int COMMENT=13;
	public static final int ELSE=14;
	public static final int EQUALS=15;
	public static final int EQUAL_OP=16;
	public static final int FLOAT=17;
	public static final int FLOAT_CONSTANT=18;
	public static final int ID=19;
	public static final int IF=20;
	public static final int INT=21;
	public static final int INT_CONSTANT=22;
	public static final int LESS_OP=23;
	public static final int MULT_OP=24;
	public static final int OPEN_BRACE=25;
	public static final int OPEN_BRACKET=26;
	public static final int OPEN_PAREN=27;
	public static final int PRINT=28;
	public static final int PRINTLN=29;
	public static final int RETURN=30;
	public static final int SEMICOLON=31;
	public static final int STRING=32;
	public static final int STRING_CONSTANT=33;
	public static final int SUB_OP=34;
	public static final int VOID=35;
	public static final int WHILE=36;
	public static final int WS=37;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public static final String[] ruleNames = new String[] {
		"invalidRule", "exprMore", "synpred18_Csc435", "synpred4_Csc435", "synpred9_Csc435", 
		"synpred21_Csc435", "lessThan", "synpred5_Csc435", "formalParameters", 
		"synpred17_Csc435", "functionBody", "equality", "synpred14_Csc435", "sub", 
		"synpred26_Csc435", "literal", "synpred32_Csc435", "synpred22_Csc435", 
		"synpred8_Csc435", "synpred2_Csc435", "synpred37_Csc435", "synpred7_Csc435", 
		"synpred15_Csc435", "type", "synpred13_Csc435", "program", "sum", "synpred10_Csc435", 
		"moreFormals", "expr", "synpred33_Csc435", "synpred34_Csc435", "synpred3_Csc435", 
		"synpred19_Csc435", "synpred27_Csc435", "id", "function", "synpred6_Csc435", 
		"synpred36_Csc435", "synpred35_Csc435", "synpred1_Csc435", "compoundType", 
		"intLiteral", "synpred25_Csc435", "synpred30_Csc435", "functionDecl", 
		"varDecl", "synpred20_Csc435", "synpred31_Csc435", "synpred28_Csc435", 
		"synpred23_Csc435", "mult", "atom", "statement", "block", "exprList", 
		"synpred24_Csc435", "synpred29_Csc435", "synpred12_Csc435", "synpred11_Csc435", 
		"formalParameter", "synpred16_Csc435"
	};

	public static final boolean[] decisionCanBacktrack = new boolean[] {
		false, // invalid decision
		false, false, false, false, false, false, false, false, false, true, false, 
		    false, false, false, true, false, false, false, false, false, false, 
		    false, false
	};

 
	public int ruleLevel = 0;
	public int getRuleLevel() { return ruleLevel; }
	public void incRuleLevel() { ruleLevel++; }
	public void decRuleLevel() { ruleLevel--; }
	public Csc435Parser(TokenStream input) {
		this(input, DebugEventSocketProxy.DEFAULT_DEBUGGER_PORT, new RecognizerSharedState());
	}
	public Csc435Parser(TokenStream input, int port, RecognizerSharedState state) {
		super(input, state);
		DebugEventSocketProxy proxy =
			new DebugEventSocketProxy(this, port, null);

		setDebugListener(proxy);
		try {
			proxy.handshake();
		}
		catch (IOException ioe) {
			reportError(ioe);
		}
	}

	public Csc435Parser(TokenStream input, DebugEventListener dbg) {
		super(input, dbg, new RecognizerSharedState());
	}

	protected boolean evalPredicate(boolean result, String predicate) {
		dbg.semanticPredicate(result, predicate);
		return result;
	}

	@Override public String[] getTokenNames() { return Csc435Parser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g"; }



	    protected void mismatch (IntStream input, int ttype, BitSet follow)
	            throws RecognitionException {
	        throw new MismatchedTokenException(ttype, input);
	    }

	    public Object recoverFromMismatchedSet (IntStream input, RecognitionException e, BitSet follow)
	            throws RecognitionException {
	        reportError(e);
	        throw e;
	    }

	    @Override
		public void reportError(RecognitionException e) {
			displayRecognitionError(this.getTokenNames(), e);
			throw new RuntimeException(e);
		}




	// $ANTLR start "program"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:60:1: program returns [Program program] : (f= function )+ EOF ;
	public final Program program() throws RecognitionException {
		Program program = null;


		Function f =null;


		        final List<Function> functions = new LinkedList<>();
		    
		try { dbg.enterRule(getGrammarFileName(), "program");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(60, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:64:5: ( (f= function )+ EOF )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:64:5: (f= function )+ EOF
			{
			dbg.location(64,5);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:64:5: (f= function )+
			int cnt1=0;
			try { dbg.enterSubRule(1);

			loop1:
			while (true) {
				int alt1=2;
				try { dbg.enterDecision(1, decisionCanBacktrack[1]);

				int LA1_0 = input.LA(1);
				if ( (LA1_0==BOOLEAN||LA1_0==CHAR||LA1_0==FLOAT||LA1_0==INT||LA1_0==STRING||LA1_0==VOID) ) {
					alt1=1;
				}

				} finally {dbg.exitDecision(1);}

				switch (alt1) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:64:6: f= function
					{
					dbg.location(64,7);
					pushFollow(FOLLOW_function_in_program102);
					f=function();
					state._fsp--;
					if (state.failed) return program;dbg.location(65,9);
					if ( state.backtracking==0 ) {
					            final boolean success = functions.add(f);

					            // Failure indicates a duplicate function.
					            assert success;
					        }
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					if (state.backtracking>0) {state.failed=true; return program;}
					EarlyExitException eee = new EarlyExitException(1, input);
					dbg.recognitionException(eee);

					throw eee;
				}
				cnt1++;
			}
			} finally {dbg.exitSubRule(1);}
			dbg.location(72,5);
			match(input,EOF,FOLLOW_EOF_in_program125); if (state.failed) return program;dbg.location(73,1);
			if ( state.backtracking==0 ) {
			    program = new Program(functions);
			}
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(75, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "program");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return program;
	}
	// $ANTLR end "program"



	// $ANTLR start "function"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:77:1: function returns [Function function] : declaration= functionDecl body= functionBody ;
	public final Function function() throws RecognitionException {
		Function function = null;


		FunctionDeclaration declaration =null;
		FunctionBody body =null;

		try { dbg.enterRule(getGrammarFileName(), "function");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(77, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:78:3: (declaration= functionDecl body= functionBody )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:78:3: declaration= functionDecl body= functionBody
			{
			dbg.location(78,14);
			pushFollow(FOLLOW_functionDecl_in_function141);
			declaration=functionDecl();
			state._fsp--;
			if (state.failed) return function;dbg.location(78,32);
			pushFollow(FOLLOW_functionBody_in_function145);
			body=functionBody();
			state._fsp--;
			if (state.failed) return function;dbg.location(79,1);
			if ( state.backtracking==0 ) {
			    return new Function(declaration, body);
			}
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(81, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "function");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return function;
	}
	// $ANTLR end "function"



	// $ANTLR start "functionDecl"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:83:1: functionDecl returns [FunctionDeclaration declaration] : t= compoundType i= id OPEN_PAREN params= formalParameters CLOSE_PAREN ;
	public final FunctionDeclaration functionDecl() throws RecognitionException {
		FunctionDeclaration declaration = null;


		TypeNode t =null;
		Identifier i =null;
		FormalParameterList params =null;

		try { dbg.enterRule(getGrammarFileName(), "functionDecl");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(83, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:84:3: (t= compoundType i= id OPEN_PAREN params= formalParameters CLOSE_PAREN )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:84:3: t= compoundType i= id OPEN_PAREN params= formalParameters CLOSE_PAREN
			{
			dbg.location(84,4);
			pushFollow(FOLLOW_compoundType_in_functionDecl161);
			t=compoundType();
			state._fsp--;
			if (state.failed) return declaration;dbg.location(84,19);
			pushFollow(FOLLOW_id_in_functionDecl165);
			i=id();
			state._fsp--;
			if (state.failed) return declaration;dbg.location(84,23);
			match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_functionDecl167); if (state.failed) return declaration;dbg.location(84,40);
			pushFollow(FOLLOW_formalParameters_in_functionDecl171);
			params=formalParameters();
			state._fsp--;
			if (state.failed) return declaration;dbg.location(84,58);
			match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_functionDecl173); if (state.failed) return declaration;dbg.location(85,1);
			if ( state.backtracking==0 ) {
			    return new FunctionDeclaration(t, i, params);
			}
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(87, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "functionDecl");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return declaration;
	}
	// $ANTLR end "functionDecl"



	// $ANTLR start "formalParameters"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:89:1: formalParameters returns [FormalParameterList paramList] : (t= compoundType i= id params= moreFormals |);
	public final FormalParameterList formalParameters() throws RecognitionException {
		FormalParameterList paramList = null;


		TypeNode t =null;
		Identifier i =null;
		List<FormalParameter> params =null;

		try { dbg.enterRule(getGrammarFileName(), "formalParameters");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(89, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:90:3: (t= compoundType i= id params= moreFormals |)
			int alt2=2;
			try { dbg.enterDecision(2, decisionCanBacktrack[2]);

			int LA2_0 = input.LA(1);
			if ( (LA2_0==BOOLEAN||LA2_0==CHAR||LA2_0==FLOAT||LA2_0==INT||LA2_0==STRING||LA2_0==VOID) ) {
				alt2=1;
			}
			else if ( (LA2_0==CLOSE_PAREN) ) {
				alt2=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return paramList;}
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				dbg.recognitionException(nvae);
				throw nvae;
			}

			} finally {dbg.exitDecision(2);}

			switch (alt2) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:90:3: t= compoundType i= id params= moreFormals
					{
					dbg.location(90,4);
					pushFollow(FOLLOW_compoundType_in_formalParameters189);
					t=compoundType();
					state._fsp--;
					if (state.failed) return paramList;dbg.location(90,19);
					pushFollow(FOLLOW_id_in_formalParameters193);
					i=id();
					state._fsp--;
					if (state.failed) return paramList;dbg.location(90,29);
					pushFollow(FOLLOW_moreFormals_in_formalParameters197);
					params=moreFormals();
					state._fsp--;
					if (state.failed) return paramList;dbg.location(91,1);
					if ( state.backtracking==0 ) {
					    final FormalParameter firstParam = new FormalParameter(t, i);
					    params.add(0, firstParam);
					    return new FormalParameterList(params);
					}
					}
					break;
				case 2 :
					dbg.enterAlt(2);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:97:1: 
					{
					dbg.location(97,1);
					if ( state.backtracking==0 ) {
					    final List<FormalParameter> parameters = Collections.emptyList();
					    return new FormalParameterList(parameters);
					}
					}
					break;

			}
		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(100, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "formalParameters");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return paramList;
	}
	// $ANTLR end "formalParameters"



	// $ANTLR start "moreFormals"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:102:1: moreFormals returns [List<FormalParameter> parameters] : ( COMMA param= formalParameter )* ;
	public final List<FormalParameter> moreFormals() throws RecognitionException {
		List<FormalParameter> parameters = null;


		FormalParameter param =null;


		        parameters = new LinkedList<>();
		    
		try { dbg.enterRule(getGrammarFileName(), "moreFormals");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(102, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:106:5: ( ( COMMA param= formalParameter )* )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:106:5: ( COMMA param= formalParameter )*
			{
			dbg.location(106,5);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:106:5: ( COMMA param= formalParameter )*
			try { dbg.enterSubRule(3);

			loop3:
			while (true) {
				int alt3=2;
				try { dbg.enterDecision(3, decisionCanBacktrack[3]);

				int LA3_0 = input.LA(1);
				if ( (LA3_0==COMMA) ) {
					alt3=1;
				}

				} finally {dbg.exitDecision(3);}

				switch (alt3) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:106:6: COMMA param= formalParameter
					{
					dbg.location(106,6);
					match(input,COMMA,FOLLOW_COMMA_in_moreFormals228); if (state.failed) return parameters;dbg.location(106,17);
					pushFollow(FOLLOW_formalParameter_in_moreFormals232);
					param=formalParameter();
					state._fsp--;
					if (state.failed) return parameters;dbg.location(107,9);
					if ( state.backtracking==0 ) {
					            parameters.add(param);
					        }
					}
					break;

				default :
					break loop3;
				}
			}
			} finally {dbg.exitSubRule(3);}
			dbg.location(111,1);
			if ( state.backtracking==0 ) {
			    return parameters;
			}
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(113, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "moreFormals");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return parameters;
	}
	// $ANTLR end "moreFormals"



	// $ANTLR start "formalParameter"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:115:1: formalParameter returns [FormalParameter parameter] : t= compoundType i= id ;
	public final FormalParameter formalParameter() throws RecognitionException {
		FormalParameter parameter = null;


		TypeNode t =null;
		Identifier i =null;

		try { dbg.enterRule(getGrammarFileName(), "formalParameter");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(115, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:116:3: (t= compoundType i= id )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:116:3: t= compoundType i= id
			{
			dbg.location(116,4);
			pushFollow(FOLLOW_compoundType_in_formalParameter265);
			t=compoundType();
			state._fsp--;
			if (state.failed) return parameter;dbg.location(116,19);
			pushFollow(FOLLOW_id_in_formalParameter269);
			i=id();
			state._fsp--;
			if (state.failed) return parameter;dbg.location(117,1);
			if ( state.backtracking==0 ) {
			    return new FormalParameter(t, i);
			}
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(119, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "formalParameter");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return parameter;
	}
	// $ANTLR end "formalParameter"



	// $ANTLR start "functionBody"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:121:1: functionBody returns [FunctionBody body] : OPEN_BRACE ( varDecl )* ( statement )* CLOSE_BRACE ;
	public final FunctionBody functionBody() throws RecognitionException {
		FunctionBody body = null;


		try { dbg.enterRule(getGrammarFileName(), "functionBody");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(121, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:122:3: ( OPEN_BRACE ( varDecl )* ( statement )* CLOSE_BRACE )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:122:3: OPEN_BRACE ( varDecl )* ( statement )* CLOSE_BRACE
			{
			dbg.location(122,3);
			match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_functionBody283); if (state.failed) return body;dbg.location(122,14);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:122:14: ( varDecl )*
			try { dbg.enterSubRule(4);

			loop4:
			while (true) {
				int alt4=2;
				try { dbg.enterDecision(4, decisionCanBacktrack[4]);

				int LA4_0 = input.LA(1);
				if ( (LA4_0==BOOLEAN||LA4_0==CHAR||LA4_0==FLOAT||LA4_0==INT||LA4_0==STRING||LA4_0==VOID) ) {
					alt4=1;
				}

				} finally {dbg.exitDecision(4);}

				switch (alt4) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:122:14: varDecl
					{
					dbg.location(122,14);
					pushFollow(FOLLOW_varDecl_in_functionBody285);
					varDecl();
					state._fsp--;
					if (state.failed) return body;
					}
					break;

				default :
					break loop4;
				}
			}
			} finally {dbg.exitSubRule(4);}
			dbg.location(122,23);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:122:23: ( statement )*
			try { dbg.enterSubRule(5);

			loop5:
			while (true) {
				int alt5=2;
				try { dbg.enterDecision(5, decisionCanBacktrack[5]);

				int LA5_0 = input.LA(1);
				if ( (LA5_0==BOOLEAN_CONSTANT||LA5_0==CHAR_CONSTANT||(LA5_0 >= FLOAT_CONSTANT && LA5_0 <= IF)||LA5_0==INT_CONSTANT||(LA5_0 >= OPEN_PAREN && LA5_0 <= SEMICOLON)||LA5_0==STRING_CONSTANT||LA5_0==WHILE) ) {
					alt5=1;
				}

				} finally {dbg.exitDecision(5);}

				switch (alt5) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:122:23: statement
					{
					dbg.location(122,23);
					pushFollow(FOLLOW_statement_in_functionBody288);
					statement();
					state._fsp--;
					if (state.failed) return body;
					}
					break;

				default :
					break loop5;
				}
			}
			} finally {dbg.exitSubRule(5);}
			dbg.location(122,34);
			match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_functionBody291); if (state.failed) return body;dbg.location(123,1);
			if ( state.backtracking==0 ) {
			    // todo
			    return new FunctionBody();
			}
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(126, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "functionBody");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return body;
	}
	// $ANTLR end "functionBody"



	// $ANTLR start "varDecl"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:128:1: varDecl : compoundType ID SEMICOLON ;
	public final void varDecl() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "varDecl");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(128, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:128:9: ( compoundType ID SEMICOLON )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:128:11: compoundType ID SEMICOLON
			{
			dbg.location(128,11);
			pushFollow(FOLLOW_compoundType_in_varDecl301);
			compoundType();
			state._fsp--;
			if (state.failed) return;dbg.location(128,24);
			match(input,ID,FOLLOW_ID_in_varDecl303); if (state.failed) return;dbg.location(128,27);
			match(input,SEMICOLON,FOLLOW_SEMICOLON_in_varDecl305); if (state.failed) return;
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(128, 36);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "varDecl");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "varDecl"



	// $ANTLR start "compoundType"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:130:1: compoundType returns [TypeNode type] : (t= type |t= type OPEN_BRACKET constant= intLiteral CLOSE_BRACKET );
	public final TypeNode compoundType() throws RecognitionException {
		TypeNode type = null;


		Type t =null;
		IntegerLiteral constant =null;

		try { dbg.enterRule(getGrammarFileName(), "compoundType");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(130, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:131:3: (t= type |t= type OPEN_BRACKET constant= intLiteral CLOSE_BRACKET )
			int alt6=2;
			try { dbg.enterDecision(6, decisionCanBacktrack[6]);

			switch ( input.LA(1) ) {
			case INT:
				{
				int LA6_1 = input.LA(2);
				if ( (LA6_1==ID) ) {
					alt6=1;
				}
				else if ( (LA6_1==OPEN_BRACKET) ) {
					alt6=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return type;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 1, input);
						dbg.recognitionException(nvae);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case FLOAT:
				{
				int LA6_2 = input.LA(2);
				if ( (LA6_2==ID) ) {
					alt6=1;
				}
				else if ( (LA6_2==OPEN_BRACKET) ) {
					alt6=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return type;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 2, input);
						dbg.recognitionException(nvae);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case CHAR:
				{
				int LA6_3 = input.LA(2);
				if ( (LA6_3==ID) ) {
					alt6=1;
				}
				else if ( (LA6_3==OPEN_BRACKET) ) {
					alt6=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return type;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 3, input);
						dbg.recognitionException(nvae);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case STRING:
				{
				int LA6_4 = input.LA(2);
				if ( (LA6_4==ID) ) {
					alt6=1;
				}
				else if ( (LA6_4==OPEN_BRACKET) ) {
					alt6=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return type;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 4, input);
						dbg.recognitionException(nvae);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case BOOLEAN:
				{
				int LA6_5 = input.LA(2);
				if ( (LA6_5==ID) ) {
					alt6=1;
				}
				else if ( (LA6_5==OPEN_BRACKET) ) {
					alt6=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return type;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 5, input);
						dbg.recognitionException(nvae);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case VOID:
				{
				int LA6_6 = input.LA(2);
				if ( (LA6_6==ID) ) {
					alt6=1;
				}
				else if ( (LA6_6==OPEN_BRACKET) ) {
					alt6=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return type;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 6, input);
						dbg.recognitionException(nvae);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return type;}
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				dbg.recognitionException(nvae);
				throw nvae;
			}
			} finally {dbg.exitDecision(6);}

			switch (alt6) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:131:3: t= type
					{
					dbg.location(131,4);
					pushFollow(FOLLOW_type_in_compoundType320);
					t=type();
					state._fsp--;
					if (state.failed) return type;dbg.location(132,1);
					if ( state.backtracking==0 ) {
					    type = t;
					}
					}
					break;
				case 2 :
					dbg.enterAlt(2);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:135:3: t= type OPEN_BRACKET constant= intLiteral CLOSE_BRACKET
					{
					dbg.location(135,4);
					pushFollow(FOLLOW_type_in_compoundType328);
					t=type();
					state._fsp--;
					if (state.failed) return type;dbg.location(135,10);
					match(input,OPEN_BRACKET,FOLLOW_OPEN_BRACKET_in_compoundType330); if (state.failed) return type;dbg.location(135,31);
					pushFollow(FOLLOW_intLiteral_in_compoundType334);
					constant=intLiteral();
					state._fsp--;
					if (state.failed) return type;dbg.location(135,43);
					match(input,CLOSE_BRACKET,FOLLOW_CLOSE_BRACKET_in_compoundType336); if (state.failed) return type;dbg.location(136,1);
					if ( state.backtracking==0 ) {
					    type = new ArrayType(t, constant);
					}
					}
					break;

			}
		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(138, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "compoundType");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return type;
	}
	// $ANTLR end "compoundType"



	// $ANTLR start "type"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:140:1: type returns [Type type] : ( INT | FLOAT | CHAR | STRING | BOOLEAN | VOID );
	public final Type type() throws RecognitionException {
		Type type = null;


		try { dbg.enterRule(getGrammarFileName(), "type");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(140, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:141:3: ( INT | FLOAT | CHAR | STRING | BOOLEAN | VOID )
			int alt7=6;
			try { dbg.enterDecision(7, decisionCanBacktrack[7]);

			switch ( input.LA(1) ) {
			case INT:
				{
				alt7=1;
				}
				break;
			case FLOAT:
				{
				alt7=2;
				}
				break;
			case CHAR:
				{
				alt7=3;
				}
				break;
			case STRING:
				{
				alt7=4;
				}
				break;
			case BOOLEAN:
				{
				alt7=5;
				}
				break;
			case VOID:
				{
				alt7=6;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return type;}
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				dbg.recognitionException(nvae);
				throw nvae;
			}
			} finally {dbg.exitDecision(7);}

			switch (alt7) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:141:3: INT
					{
					dbg.location(141,3);
					match(input,INT,FOLLOW_INT_in_type350); if (state.failed) return type;dbg.location(142,1);
					if ( state.backtracking==0 ) {
					    return Type.INT;
					}
					}
					break;
				case 2 :
					dbg.enterAlt(2);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:145:3: FLOAT
					{
					dbg.location(145,3);
					match(input,FLOAT,FOLLOW_FLOAT_in_type356); if (state.failed) return type;dbg.location(146,1);
					if ( state.backtracking==0 ) {
					    return Type.FLOAT;
					}
					}
					break;
				case 3 :
					dbg.enterAlt(3);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:149:3: CHAR
					{
					dbg.location(149,3);
					match(input,CHAR,FOLLOW_CHAR_in_type362); if (state.failed) return type;dbg.location(150,1);
					if ( state.backtracking==0 ) {
					    return Type.CHAR;
					}
					}
					break;
				case 4 :
					dbg.enterAlt(4);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:153:3: STRING
					{
					dbg.location(153,3);
					match(input,STRING,FOLLOW_STRING_in_type368); if (state.failed) return type;dbg.location(154,1);
					if ( state.backtracking==0 ) {
					    return Type.STRING;
					}
					}
					break;
				case 5 :
					dbg.enterAlt(5);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:157:3: BOOLEAN
					{
					dbg.location(157,3);
					match(input,BOOLEAN,FOLLOW_BOOLEAN_in_type374); if (state.failed) return type;dbg.location(158,1);
					if ( state.backtracking==0 ) {
					    return Type.BOOLEAN;
					}
					}
					break;
				case 6 :
					dbg.enterAlt(6);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:161:3: VOID
					{
					dbg.location(161,3);
					match(input,VOID,FOLLOW_VOID_in_type380); if (state.failed) return type;dbg.location(162,1);
					if ( state.backtracking==0 ) {
					    return Type.VOID;
					}
					}
					break;

			}
		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(164, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "type");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return type;
	}
	// $ANTLR end "type"



	// $ANTLR start "statement"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:166:1: statement : ( SEMICOLON | expr SEMICOLON | IF OPEN_PAREN expr CLOSE_PAREN block ( ELSE block )? | WHILE OPEN_PAREN expr CLOSE_PAREN block | PRINT expr SEMICOLON | PRINTLN expr SEMICOLON | RETURN ( expr )? SEMICOLON | ID EQUALS expr SEMICOLON | ID OPEN_BRACKET expr CLOSE_BRACKET EQUALS expr SEMICOLON );
	public final void statement() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "statement");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(166, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:166:13: ( SEMICOLON | expr SEMICOLON | IF OPEN_PAREN expr CLOSE_PAREN block ( ELSE block )? | WHILE OPEN_PAREN expr CLOSE_PAREN block | PRINT expr SEMICOLON | PRINTLN expr SEMICOLON | RETURN ( expr )? SEMICOLON | ID EQUALS expr SEMICOLON | ID OPEN_BRACKET expr CLOSE_BRACKET EQUALS expr SEMICOLON )
			int alt10=9;
			try { dbg.enterDecision(10, decisionCanBacktrack[10]);

			switch ( input.LA(1) ) {
			case SEMICOLON:
				{
				alt10=1;
				}
				break;
			case BOOLEAN_CONSTANT:
			case CHAR_CONSTANT:
			case FLOAT_CONSTANT:
			case INT_CONSTANT:
			case OPEN_PAREN:
			case STRING_CONSTANT:
				{
				alt10=2;
				}
				break;
			case ID:
				{
				int LA10_7 = input.LA(2);
				if ( (synpred13_Csc435()) ) {
					alt10=2;
				}
				else if ( (synpred21_Csc435()) ) {
					alt10=8;
				}
				else if ( (true) ) {
					alt10=9;
				}

				}
				break;
			case IF:
				{
				alt10=3;
				}
				break;
			case WHILE:
				{
				alt10=4;
				}
				break;
			case PRINT:
				{
				alt10=5;
				}
				break;
			case PRINTLN:
				{
				alt10=6;
				}
				break;
			case RETURN:
				{
				alt10=7;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				dbg.recognitionException(nvae);
				throw nvae;
			}
			} finally {dbg.exitDecision(10);}

			switch (alt10) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:166:15: SEMICOLON
					{
					dbg.location(166,15);
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement392); if (state.failed) return;
					}
					break;
				case 2 :
					dbg.enterAlt(2);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:167:15: expr SEMICOLON
					{
					dbg.location(167,15);
					pushFollow(FOLLOW_expr_in_statement408);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(167,20);
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement410); if (state.failed) return;
					}
					break;
				case 3 :
					dbg.enterAlt(3);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:168:15: IF OPEN_PAREN expr CLOSE_PAREN block ( ELSE block )?
					{
					dbg.location(168,15);
					match(input,IF,FOLLOW_IF_in_statement426); if (state.failed) return;dbg.location(168,18);
					match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_statement428); if (state.failed) return;dbg.location(168,29);
					pushFollow(FOLLOW_expr_in_statement430);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(168,34);
					match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_statement432); if (state.failed) return;dbg.location(168,46);
					pushFollow(FOLLOW_block_in_statement434);
					block();
					state._fsp--;
					if (state.failed) return;dbg.location(168,52);
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:168:52: ( ELSE block )?
					int alt8=2;
					try { dbg.enterSubRule(8);
					try { dbg.enterDecision(8, decisionCanBacktrack[8]);

					int LA8_0 = input.LA(1);
					if ( (LA8_0==ELSE) ) {
						alt8=1;
					}
					} finally {dbg.exitDecision(8);}

					switch (alt8) {
						case 1 :
							dbg.enterAlt(1);

							// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:168:53: ELSE block
							{
							dbg.location(168,53);
							match(input,ELSE,FOLLOW_ELSE_in_statement437); if (state.failed) return;dbg.location(168,58);
							pushFollow(FOLLOW_block_in_statement439);
							block();
							state._fsp--;
							if (state.failed) return;
							}
							break;

					}
					} finally {dbg.exitSubRule(8);}

					}
					break;
				case 4 :
					dbg.enterAlt(4);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:169:15: WHILE OPEN_PAREN expr CLOSE_PAREN block
					{
					dbg.location(169,15);
					match(input,WHILE,FOLLOW_WHILE_in_statement457); if (state.failed) return;dbg.location(169,21);
					match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_statement459); if (state.failed) return;dbg.location(169,32);
					pushFollow(FOLLOW_expr_in_statement461);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(169,37);
					match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_statement463); if (state.failed) return;dbg.location(169,49);
					pushFollow(FOLLOW_block_in_statement465);
					block();
					state._fsp--;
					if (state.failed) return;
					}
					break;
				case 5 :
					dbg.enterAlt(5);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:170:15: PRINT expr SEMICOLON
					{
					dbg.location(170,15);
					match(input,PRINT,FOLLOW_PRINT_in_statement481); if (state.failed) return;dbg.location(170,21);
					pushFollow(FOLLOW_expr_in_statement483);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(170,26);
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement485); if (state.failed) return;
					}
					break;
				case 6 :
					dbg.enterAlt(6);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:171:15: PRINTLN expr SEMICOLON
					{
					dbg.location(171,15);
					match(input,PRINTLN,FOLLOW_PRINTLN_in_statement501); if (state.failed) return;dbg.location(171,23);
					pushFollow(FOLLOW_expr_in_statement503);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(171,28);
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement505); if (state.failed) return;
					}
					break;
				case 7 :
					dbg.enterAlt(7);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:172:15: RETURN ( expr )? SEMICOLON
					{
					dbg.location(172,15);
					match(input,RETURN,FOLLOW_RETURN_in_statement521); if (state.failed) return;dbg.location(172,22);
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:172:22: ( expr )?
					int alt9=2;
					try { dbg.enterSubRule(9);
					try { dbg.enterDecision(9, decisionCanBacktrack[9]);

					int LA9_0 = input.LA(1);
					if ( (LA9_0==BOOLEAN_CONSTANT||LA9_0==CHAR_CONSTANT||(LA9_0 >= FLOAT_CONSTANT && LA9_0 <= ID)||LA9_0==INT_CONSTANT||LA9_0==OPEN_PAREN||LA9_0==STRING_CONSTANT) ) {
						alt9=1;
					}
					} finally {dbg.exitDecision(9);}

					switch (alt9) {
						case 1 :
							dbg.enterAlt(1);

							// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:172:22: expr
							{
							dbg.location(172,22);
							pushFollow(FOLLOW_expr_in_statement523);
							expr();
							state._fsp--;
							if (state.failed) return;
							}
							break;

					}
					} finally {dbg.exitSubRule(9);}
					dbg.location(172,28);
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement526); if (state.failed) return;
					}
					break;
				case 8 :
					dbg.enterAlt(8);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:173:15: ID EQUALS expr SEMICOLON
					{
					dbg.location(173,15);
					match(input,ID,FOLLOW_ID_in_statement542); if (state.failed) return;dbg.location(173,18);
					match(input,EQUALS,FOLLOW_EQUALS_in_statement544); if (state.failed) return;dbg.location(173,25);
					pushFollow(FOLLOW_expr_in_statement546);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(173,30);
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement548); if (state.failed) return;
					}
					break;
				case 9 :
					dbg.enterAlt(9);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:174:15: ID OPEN_BRACKET expr CLOSE_BRACKET EQUALS expr SEMICOLON
					{
					dbg.location(174,15);
					match(input,ID,FOLLOW_ID_in_statement564); if (state.failed) return;dbg.location(174,18);
					match(input,OPEN_BRACKET,FOLLOW_OPEN_BRACKET_in_statement566); if (state.failed) return;dbg.location(174,31);
					pushFollow(FOLLOW_expr_in_statement568);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(174,36);
					match(input,CLOSE_BRACKET,FOLLOW_CLOSE_BRACKET_in_statement570); if (state.failed) return;dbg.location(174,50);
					match(input,EQUALS,FOLLOW_EQUALS_in_statement572); if (state.failed) return;dbg.location(174,57);
					pushFollow(FOLLOW_expr_in_statement574);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(174,62);
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement576); if (state.failed) return;
					}
					break;

			}
		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(175, 12);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "statement");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "statement"



	// $ANTLR start "block"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:177:1: block : OPEN_BRACE ( statement )* CLOSE_BRACE ;
	public final void block() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "block");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(177, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:177:7: ( OPEN_BRACE ( statement )* CLOSE_BRACE )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:177:9: OPEN_BRACE ( statement )* CLOSE_BRACE
			{
			dbg.location(177,9);
			match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_block597); if (state.failed) return;dbg.location(177,20);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:177:20: ( statement )*
			try { dbg.enterSubRule(11);

			loop11:
			while (true) {
				int alt11=2;
				try { dbg.enterDecision(11, decisionCanBacktrack[11]);

				int LA11_0 = input.LA(1);
				if ( (LA11_0==BOOLEAN_CONSTANT||LA11_0==CHAR_CONSTANT||(LA11_0 >= FLOAT_CONSTANT && LA11_0 <= IF)||LA11_0==INT_CONSTANT||(LA11_0 >= OPEN_PAREN && LA11_0 <= SEMICOLON)||LA11_0==STRING_CONSTANT||LA11_0==WHILE) ) {
					alt11=1;
				}

				} finally {dbg.exitDecision(11);}

				switch (alt11) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:177:20: statement
					{
					dbg.location(177,20);
					pushFollow(FOLLOW_statement_in_block599);
					statement();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop11;
				}
			}
			} finally {dbg.exitSubRule(11);}
			dbg.location(177,31);
			match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_block602); if (state.failed) return;
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(177, 42);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "block");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "block"



	// $ANTLR start "expr"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:179:1: expr : equality ;
	public final void expr() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "expr");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(179, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:179:6: ( equality )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:179:8: equality
			{
			dbg.location(179,8);
			pushFollow(FOLLOW_equality_in_expr611);
			equality();
			state._fsp--;
			if (state.failed) return;
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(179, 16);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "expr");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "expr"



	// $ANTLR start "equality"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:181:1: equality : lessThan ( EQUAL_OP lessThan )* ;
	public final void equality() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "equality");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(181, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:181:10: ( lessThan ( EQUAL_OP lessThan )* )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:181:12: lessThan ( EQUAL_OP lessThan )*
			{
			dbg.location(181,12);
			pushFollow(FOLLOW_lessThan_in_equality620);
			lessThan();
			state._fsp--;
			if (state.failed) return;dbg.location(181,21);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:181:21: ( EQUAL_OP lessThan )*
			try { dbg.enterSubRule(12);

			loop12:
			while (true) {
				int alt12=2;
				try { dbg.enterDecision(12, decisionCanBacktrack[12]);

				int LA12_0 = input.LA(1);
				if ( (LA12_0==EQUAL_OP) ) {
					alt12=1;
				}

				} finally {dbg.exitDecision(12);}

				switch (alt12) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:181:22: EQUAL_OP lessThan
					{
					dbg.location(181,22);
					match(input,EQUAL_OP,FOLLOW_EQUAL_OP_in_equality623); if (state.failed) return;dbg.location(181,31);
					pushFollow(FOLLOW_lessThan_in_equality625);
					lessThan();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop12;
				}
			}
			} finally {dbg.exitSubRule(12);}

			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(181, 41);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "equality");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "equality"



	// $ANTLR start "lessThan"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:183:1: lessThan : sum ( LESS_OP sum )* ;
	public final void lessThan() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "lessThan");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(183, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:183:10: ( sum ( LESS_OP sum )* )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:183:12: sum ( LESS_OP sum )*
			{
			dbg.location(183,12);
			pushFollow(FOLLOW_sum_in_lessThan636);
			sum();
			state._fsp--;
			if (state.failed) return;dbg.location(183,16);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:183:16: ( LESS_OP sum )*
			try { dbg.enterSubRule(13);

			loop13:
			while (true) {
				int alt13=2;
				try { dbg.enterDecision(13, decisionCanBacktrack[13]);

				int LA13_0 = input.LA(1);
				if ( (LA13_0==LESS_OP) ) {
					alt13=1;
				}

				} finally {dbg.exitDecision(13);}

				switch (alt13) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:183:17: LESS_OP sum
					{
					dbg.location(183,17);
					match(input,LESS_OP,FOLLOW_LESS_OP_in_lessThan639); if (state.failed) return;dbg.location(183,25);
					pushFollow(FOLLOW_sum_in_lessThan641);
					sum();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop13;
				}
			}
			} finally {dbg.exitSubRule(13);}

			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(183, 30);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "lessThan");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "lessThan"



	// $ANTLR start "sum"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:185:1: sum : sub ( ADD_OP sub )* ;
	public final void sum() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "sum");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(185, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:185:5: ( sub ( ADD_OP sub )* )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:185:7: sub ( ADD_OP sub )*
			{
			dbg.location(185,7);
			pushFollow(FOLLOW_sub_in_sum652);
			sub();
			state._fsp--;
			if (state.failed) return;dbg.location(185,11);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:185:11: ( ADD_OP sub )*
			try { dbg.enterSubRule(14);

			loop14:
			while (true) {
				int alt14=2;
				try { dbg.enterDecision(14, decisionCanBacktrack[14]);

				int LA14_0 = input.LA(1);
				if ( (LA14_0==ADD_OP) ) {
					alt14=1;
				}

				} finally {dbg.exitDecision(14);}

				switch (alt14) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:185:12: ADD_OP sub
					{
					dbg.location(185,12);
					match(input,ADD_OP,FOLLOW_ADD_OP_in_sum655); if (state.failed) return;dbg.location(185,19);
					pushFollow(FOLLOW_sub_in_sum657);
					sub();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop14;
				}
			}
			} finally {dbg.exitSubRule(14);}

			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(185, 24);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "sum");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "sum"



	// $ANTLR start "sub"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:187:1: sub : mult ( SUB_OP sub )* ;
	public final void sub() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "sub");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(187, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:187:5: ( mult ( SUB_OP sub )* )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:187:7: mult ( SUB_OP sub )*
			{
			dbg.location(187,7);
			pushFollow(FOLLOW_mult_in_sub668);
			mult();
			state._fsp--;
			if (state.failed) return;dbg.location(187,12);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:187:12: ( SUB_OP sub )*
			try { dbg.enterSubRule(15);

			loop15:
			while (true) {
				int alt15=2;
				try { dbg.enterDecision(15, decisionCanBacktrack[15]);

				int LA15_0 = input.LA(1);
				if ( (LA15_0==SUB_OP) ) {
					int LA15_2 = input.LA(2);
					if ( (synpred26_Csc435()) ) {
						alt15=1;
					}

				}

				} finally {dbg.exitDecision(15);}

				switch (alt15) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:187:13: SUB_OP sub
					{
					dbg.location(187,13);
					match(input,SUB_OP,FOLLOW_SUB_OP_in_sub671); if (state.failed) return;dbg.location(187,20);
					pushFollow(FOLLOW_sub_in_sub673);
					sub();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop15;
				}
			}
			} finally {dbg.exitSubRule(15);}

			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(187, 25);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "sub");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "sub"



	// $ANTLR start "mult"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:189:1: mult : atom ( MULT_OP atom )* ;
	public final void mult() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "mult");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(189, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:189:6: ( atom ( MULT_OP atom )* )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:189:8: atom ( MULT_OP atom )*
			{
			dbg.location(189,8);
			pushFollow(FOLLOW_atom_in_mult684);
			atom();
			state._fsp--;
			if (state.failed) return;dbg.location(189,13);
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:189:13: ( MULT_OP atom )*
			try { dbg.enterSubRule(16);

			loop16:
			while (true) {
				int alt16=2;
				try { dbg.enterDecision(16, decisionCanBacktrack[16]);

				int LA16_0 = input.LA(1);
				if ( (LA16_0==MULT_OP) ) {
					alt16=1;
				}

				} finally {dbg.exitDecision(16);}

				switch (alt16) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:189:14: MULT_OP atom
					{
					dbg.location(189,14);
					match(input,MULT_OP,FOLLOW_MULT_OP_in_mult687); if (state.failed) return;dbg.location(189,22);
					pushFollow(FOLLOW_atom_in_mult689);
					atom();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop16;
				}
			}
			} finally {dbg.exitSubRule(16);}

			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(189, 28);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "mult");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "mult"



	// $ANTLR start "atom"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:191:1: atom : ( literal | ID | OPEN_PAREN expr CLOSE_PAREN | ID OPEN_BRACKET expr CLOSE_BRACKET | ID OPEN_PAREN exprList CLOSE_PAREN );
	public final void atom() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "atom");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(191, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:191:9: ( literal | ID | OPEN_PAREN expr CLOSE_PAREN | ID OPEN_BRACKET expr CLOSE_BRACKET | ID OPEN_PAREN exprList CLOSE_PAREN )
			int alt17=5;
			try { dbg.enterDecision(17, decisionCanBacktrack[17]);

			switch ( input.LA(1) ) {
			case BOOLEAN_CONSTANT:
			case CHAR_CONSTANT:
			case FLOAT_CONSTANT:
			case INT_CONSTANT:
			case STRING_CONSTANT:
				{
				alt17=1;
				}
				break;
			case ID:
				{
				switch ( input.LA(2) ) {
				case OPEN_BRACKET:
					{
					alt17=4;
					}
					break;
				case OPEN_PAREN:
					{
					alt17=5;
					}
					break;
				case EOF:
				case ADD_OP:
				case CLOSE_BRACKET:
				case CLOSE_PAREN:
				case COMMA:
				case EQUAL_OP:
				case LESS_OP:
				case MULT_OP:
				case SEMICOLON:
				case SUB_OP:
					{
					alt17=2;
					}
					break;
				default:
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 17, 2, input);
						dbg.recognitionException(nvae);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case OPEN_PAREN:
				{
				alt17=3;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 17, 0, input);
				dbg.recognitionException(nvae);
				throw nvae;
			}
			} finally {dbg.exitDecision(17);}

			switch (alt17) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:191:11: literal
					{
					dbg.location(191,11);
					pushFollow(FOLLOW_literal_in_atom703);
					literal();
					state._fsp--;
					if (state.failed) return;
					}
					break;
				case 2 :
					dbg.enterAlt(2);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:192:11: ID
					{
					dbg.location(192,11);
					match(input,ID,FOLLOW_ID_in_atom715); if (state.failed) return;
					}
					break;
				case 3 :
					dbg.enterAlt(3);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:193:11: OPEN_PAREN expr CLOSE_PAREN
					{
					dbg.location(193,11);
					match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_atom727); if (state.failed) return;dbg.location(193,22);
					pushFollow(FOLLOW_expr_in_atom729);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(193,27);
					match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_atom731); if (state.failed) return;
					}
					break;
				case 4 :
					dbg.enterAlt(4);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:194:11: ID OPEN_BRACKET expr CLOSE_BRACKET
					{
					dbg.location(194,11);
					match(input,ID,FOLLOW_ID_in_atom743); if (state.failed) return;dbg.location(194,14);
					match(input,OPEN_BRACKET,FOLLOW_OPEN_BRACKET_in_atom745); if (state.failed) return;dbg.location(194,27);
					pushFollow(FOLLOW_expr_in_atom747);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(194,32);
					match(input,CLOSE_BRACKET,FOLLOW_CLOSE_BRACKET_in_atom749); if (state.failed) return;
					}
					break;
				case 5 :
					dbg.enterAlt(5);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:195:11: ID OPEN_PAREN exprList CLOSE_PAREN
					{
					dbg.location(195,11);
					match(input,ID,FOLLOW_ID_in_atom761); if (state.failed) return;dbg.location(195,14);
					match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_atom763); if (state.failed) return;dbg.location(195,25);
					pushFollow(FOLLOW_exprList_in_atom765);
					exprList();
					state._fsp--;
					if (state.failed) return;dbg.location(195,34);
					match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_atom767); if (state.failed) return;
					}
					break;

			}
		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(196, 8);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "atom");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "atom"



	// $ANTLR start "literal"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:198:1: literal : ( STRING_CONSTANT | intLiteral | FLOAT_CONSTANT | CHAR_CONSTANT | BOOLEAN_CONSTANT );
	public final void literal() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "literal");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(198, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:198:9: ( STRING_CONSTANT | intLiteral | FLOAT_CONSTANT | CHAR_CONSTANT | BOOLEAN_CONSTANT )
			int alt18=5;
			try { dbg.enterDecision(18, decisionCanBacktrack[18]);

			switch ( input.LA(1) ) {
			case STRING_CONSTANT:
				{
				alt18=1;
				}
				break;
			case INT_CONSTANT:
				{
				alt18=2;
				}
				break;
			case FLOAT_CONSTANT:
				{
				alt18=3;
				}
				break;
			case CHAR_CONSTANT:
				{
				alt18=4;
				}
				break;
			case BOOLEAN_CONSTANT:
				{
				alt18=5;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 18, 0, input);
				dbg.recognitionException(nvae);
				throw nvae;
			}
			} finally {dbg.exitDecision(18);}

			switch (alt18) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:198:11: STRING_CONSTANT
					{
					dbg.location(198,11);
					match(input,STRING_CONSTANT,FOLLOW_STRING_CONSTANT_in_literal784); if (state.failed) return;
					}
					break;
				case 2 :
					dbg.enterAlt(2);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:199:11: intLiteral
					{
					dbg.location(199,11);
					pushFollow(FOLLOW_intLiteral_in_literal796);
					intLiteral();
					state._fsp--;
					if (state.failed) return;
					}
					break;
				case 3 :
					dbg.enterAlt(3);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:200:11: FLOAT_CONSTANT
					{
					dbg.location(200,11);
					match(input,FLOAT_CONSTANT,FOLLOW_FLOAT_CONSTANT_in_literal808); if (state.failed) return;
					}
					break;
				case 4 :
					dbg.enterAlt(4);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:201:11: CHAR_CONSTANT
					{
					dbg.location(201,11);
					match(input,CHAR_CONSTANT,FOLLOW_CHAR_CONSTANT_in_literal820); if (state.failed) return;
					}
					break;
				case 5 :
					dbg.enterAlt(5);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:202:11: BOOLEAN_CONSTANT
					{
					dbg.location(202,11);
					match(input,BOOLEAN_CONSTANT,FOLLOW_BOOLEAN_CONSTANT_in_literal832); if (state.failed) return;
					}
					break;

			}
		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(203, 8);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "literal");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "literal"



	// $ANTLR start "intLiteral"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:205:1: intLiteral returns [IntegerLiteral literal] : constant= INT_CONSTANT ;
	public final IntegerLiteral intLiteral() throws RecognitionException {
		IntegerLiteral literal = null;


		Token constant=null;

		try { dbg.enterRule(getGrammarFileName(), "intLiteral");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(205, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:206:3: (constant= INT_CONSTANT )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:206:3: constant= INT_CONSTANT
			{
			dbg.location(206,11);
			constant=(Token)match(input,INT_CONSTANT,FOLLOW_INT_CONSTANT_in_intLiteral855); if (state.failed) return literal;dbg.location(207,1);
			if ( state.backtracking==0 ) {
			    final int value = Integer.parseInt(constant.getText());
			    literal = new IntegerLiteral(value);
			}
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(210, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "intLiteral");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return literal;
	}
	// $ANTLR end "intLiteral"



	// $ANTLR start "id"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:212:1: id returns [Identifier id] : i= ID ;
	public final Identifier id() throws RecognitionException {
		Identifier id = null;


		Token i=null;

		try { dbg.enterRule(getGrammarFileName(), "id");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(212, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:213:3: (i= ID )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:213:3: i= ID
			{
			dbg.location(213,4);
			i=(Token)match(input,ID,FOLLOW_ID_in_id871); if (state.failed) return id;dbg.location(214,1);
			if ( state.backtracking==0 ) {
			    final String text = i.getText();
			    id = new Identifier(text);
			}
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(217, 1);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "id");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

		return id;
	}
	// $ANTLR end "id"



	// $ANTLR start "exprList"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:219:1: exprList : ( expr ( exprMore )* |);
	public final void exprList() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "exprList");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(219, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:219:13: ( expr ( exprMore )* |)
			int alt20=2;
			try { dbg.enterDecision(20, decisionCanBacktrack[20]);

			int LA20_0 = input.LA(1);
			if ( (LA20_0==BOOLEAN_CONSTANT||LA20_0==CHAR_CONSTANT||(LA20_0 >= FLOAT_CONSTANT && LA20_0 <= ID)||LA20_0==INT_CONSTANT||LA20_0==OPEN_PAREN||LA20_0==STRING_CONSTANT) ) {
				alt20=1;
			}
			else if ( (LA20_0==CLOSE_PAREN) ) {
				alt20=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 20, 0, input);
				dbg.recognitionException(nvae);
				throw nvae;
			}

			} finally {dbg.exitDecision(20);}

			switch (alt20) {
				case 1 :
					dbg.enterAlt(1);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:219:15: expr ( exprMore )*
					{
					dbg.location(219,15);
					pushFollow(FOLLOW_expr_in_exprList884);
					expr();
					state._fsp--;
					if (state.failed) return;dbg.location(219,20);
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:219:20: ( exprMore )*
					try { dbg.enterSubRule(19);

					loop19:
					while (true) {
						int alt19=2;
						try { dbg.enterDecision(19, decisionCanBacktrack[19]);

						int LA19_0 = input.LA(1);
						if ( (LA19_0==COMMA) ) {
							alt19=1;
						}

						} finally {dbg.exitDecision(19);}

						switch (alt19) {
						case 1 :
							dbg.enterAlt(1);

							// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:219:20: exprMore
							{
							dbg.location(219,20);
							pushFollow(FOLLOW_exprMore_in_exprList886);
							exprMore();
							state._fsp--;
							if (state.failed) return;
							}
							break;

						default :
							break loop19;
						}
					}
					} finally {dbg.exitSubRule(19);}

					}
					break;
				case 2 :
					dbg.enterAlt(2);

					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:221:13: 
					{
					}
					break;

			}
		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(221, 12);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "exprList");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "exprList"



	// $ANTLR start "exprMore"
	// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:223:1: exprMore : COMMA expr ;
	public final void exprMore() throws RecognitionException {
		try { dbg.enterRule(getGrammarFileName(), "exprMore");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(223, 0);

		try {
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:223:10: ( COMMA expr )
			dbg.enterAlt(1);

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:223:12: COMMA expr
			{
			dbg.location(223,12);
			match(input,COMMA,FOLLOW_COMMA_in_exprMore923); if (state.failed) return;dbg.location(223,18);
			pushFollow(FOLLOW_expr_in_exprMore925);
			expr();
			state._fsp--;
			if (state.failed) return;
			}

		}

		    catch (RecognitionException ex) {
		        reportError(ex);
		        throw ex;
		    }

		finally {
			// do for sure before leaving
		}
		dbg.location(223, 22);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "exprMore");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "exprMore"

	// $ANTLR start synpred13_Csc435
	public final void synpred13_Csc435_fragment() throws RecognitionException {
		// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:167:15: ( expr SEMICOLON )
		dbg.enterAlt(1);

		// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:167:15: expr SEMICOLON
		{
		dbg.location(167,15);
		pushFollow(FOLLOW_expr_in_synpred13_Csc435408);
		expr();
		state._fsp--;
		if (state.failed) return;dbg.location(167,20);
		match(input,SEMICOLON,FOLLOW_SEMICOLON_in_synpred13_Csc435410); if (state.failed) return;
		}

	}
	// $ANTLR end synpred13_Csc435

	// $ANTLR start synpred21_Csc435
	public final void synpred21_Csc435_fragment() throws RecognitionException {
		// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:173:15: ( ID EQUALS expr SEMICOLON )
		dbg.enterAlt(1);

		// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:173:15: ID EQUALS expr SEMICOLON
		{
		dbg.location(173,15);
		match(input,ID,FOLLOW_ID_in_synpred21_Csc435542); if (state.failed) return;dbg.location(173,18);
		match(input,EQUALS,FOLLOW_EQUALS_in_synpred21_Csc435544); if (state.failed) return;dbg.location(173,25);
		pushFollow(FOLLOW_expr_in_synpred21_Csc435546);
		expr();
		state._fsp--;
		if (state.failed) return;dbg.location(173,30);
		match(input,SEMICOLON,FOLLOW_SEMICOLON_in_synpred21_Csc435548); if (state.failed) return;
		}

	}
	// $ANTLR end synpred21_Csc435

	// $ANTLR start synpred26_Csc435
	public final void synpred26_Csc435_fragment() throws RecognitionException {
		// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:187:13: ( SUB_OP sub )
		dbg.enterAlt(1);

		// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:187:13: SUB_OP sub
		{
		dbg.location(187,13);
		match(input,SUB_OP,FOLLOW_SUB_OP_in_synpred26_Csc435671); if (state.failed) return;dbg.location(187,20);
		pushFollow(FOLLOW_sub_in_synpred26_Csc435673);
		sub();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred26_Csc435

	// Delegated rules

	public final boolean synpred26_Csc435() {
		state.backtracking++;
		dbg.beginBacktrack(state.backtracking);
		int start = input.mark();
		try {
			synpred26_Csc435_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		dbg.endBacktrack(state.backtracking, success);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred13_Csc435() {
		state.backtracking++;
		dbg.beginBacktrack(state.backtracking);
		int start = input.mark();
		try {
			synpred13_Csc435_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		dbg.endBacktrack(state.backtracking, success);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred21_Csc435() {
		state.backtracking++;
		dbg.beginBacktrack(state.backtracking);
		int start = input.mark();
		try {
			synpred21_Csc435_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		dbg.endBacktrack(state.backtracking, success);
		state.backtracking--;
		state.failed=false;
		return success;
	}



	public static final BitSet FOLLOW_function_in_program102 = new BitSet(new long[]{0x00000009002200A0L});
	public static final BitSet FOLLOW_EOF_in_program125 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionDecl_in_function141 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_functionBody_in_function145 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_compoundType_in_functionDecl161 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_id_in_functionDecl165 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_OPEN_PAREN_in_functionDecl167 = new BitSet(new long[]{0x00000009002208A0L});
	public static final BitSet FOLLOW_formalParameters_in_functionDecl171 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_CLOSE_PAREN_in_functionDecl173 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_compoundType_in_formalParameters189 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_id_in_formalParameters193 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_moreFormals_in_formalParameters197 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COMMA_in_moreFormals228 = new BitSet(new long[]{0x00000009002200A0L});
	public static final BitSet FOLLOW_formalParameter_in_moreFormals232 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_compoundType_in_formalParameter265 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_id_in_formalParameter269 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_functionBody283 = new BitSet(new long[]{0x0000001BF87E03E0L});
	public static final BitSet FOLLOW_varDecl_in_functionBody285 = new BitSet(new long[]{0x0000001BF87E03E0L});
	public static final BitSet FOLLOW_statement_in_functionBody288 = new BitSet(new long[]{0x00000012F85C0340L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_functionBody291 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_compoundType_in_varDecl301 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_ID_in_varDecl303 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_varDecl305 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_compoundType320 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_compoundType328 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_OPEN_BRACKET_in_compoundType330 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_intLiteral_in_compoundType334 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_CLOSE_BRACKET_in_compoundType336 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_type350 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FLOAT_in_type356 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CHAR_in_type362 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_type368 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BOOLEAN_in_type374 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VOID_in_type380 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement392 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_statement408 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement410 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IF_in_statement426 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_OPEN_PAREN_in_statement428 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_statement430 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_CLOSE_PAREN_in_statement432 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_block_in_statement434 = new BitSet(new long[]{0x0000000000004002L});
	public static final BitSet FOLLOW_ELSE_in_statement437 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_block_in_statement439 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WHILE_in_statement457 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_OPEN_PAREN_in_statement459 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_statement461 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_CLOSE_PAREN_in_statement463 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_block_in_statement465 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PRINT_in_statement481 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_statement483 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement485 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PRINTLN_in_statement501 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_statement503 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement505 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_RETURN_in_statement521 = new BitSet(new long[]{0x00000002884C0140L});
	public static final BitSet FOLLOW_expr_in_statement523 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement526 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_statement542 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_EQUALS_in_statement544 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_statement546 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement548 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_statement564 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_OPEN_BRACKET_in_statement566 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_statement568 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_CLOSE_BRACKET_in_statement570 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_EQUALS_in_statement572 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_statement574 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement576 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_block597 = new BitSet(new long[]{0x00000012F85C0340L});
	public static final BitSet FOLLOW_statement_in_block599 = new BitSet(new long[]{0x00000012F85C0340L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_block602 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_equality_in_expr611 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_lessThan_in_equality620 = new BitSet(new long[]{0x0000000000010002L});
	public static final BitSet FOLLOW_EQUAL_OP_in_equality623 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_lessThan_in_equality625 = new BitSet(new long[]{0x0000000000010002L});
	public static final BitSet FOLLOW_sum_in_lessThan636 = new BitSet(new long[]{0x0000000000800002L});
	public static final BitSet FOLLOW_LESS_OP_in_lessThan639 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_sum_in_lessThan641 = new BitSet(new long[]{0x0000000000800002L});
	public static final BitSet FOLLOW_sub_in_sum652 = new BitSet(new long[]{0x0000000000000012L});
	public static final BitSet FOLLOW_ADD_OP_in_sum655 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_sub_in_sum657 = new BitSet(new long[]{0x0000000000000012L});
	public static final BitSet FOLLOW_mult_in_sub668 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_SUB_OP_in_sub671 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_sub_in_sub673 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_atom_in_mult684 = new BitSet(new long[]{0x0000000001000002L});
	public static final BitSet FOLLOW_MULT_OP_in_mult687 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_atom_in_mult689 = new BitSet(new long[]{0x0000000001000002L});
	public static final BitSet FOLLOW_literal_in_atom703 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_atom715 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_PAREN_in_atom727 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_atom729 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_CLOSE_PAREN_in_atom731 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_atom743 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_OPEN_BRACKET_in_atom745 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_atom747 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_CLOSE_BRACKET_in_atom749 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_atom761 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_OPEN_PAREN_in_atom763 = new BitSet(new long[]{0x00000002084C0940L});
	public static final BitSet FOLLOW_exprList_in_atom765 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_CLOSE_PAREN_in_atom767 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_CONSTANT_in_literal784 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_intLiteral_in_literal796 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FLOAT_CONSTANT_in_literal808 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CHAR_CONSTANT_in_literal820 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BOOLEAN_CONSTANT_in_literal832 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_CONSTANT_in_intLiteral855 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_id871 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_exprList884 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_exprMore_in_exprList886 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_COMMA_in_exprMore923 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_exprMore925 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_synpred13_Csc435408 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_synpred13_Csc435410 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_synpred21_Csc435542 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_EQUALS_in_synpred21_Csc435544 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_expr_in_synpred21_Csc435546 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_synpred21_Csc435548 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SUB_OP_in_synpred26_Csc435671 = new BitSet(new long[]{0x00000002084C0140L});
	public static final BitSet FOLLOW_sub_in_synpred26_Csc435673 = new BitSet(new long[]{0x0000000000000002L});
}
