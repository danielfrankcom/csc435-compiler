// $ANTLR null /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g 2020-01-19 10:23:38

    package om.frankc.csc435.compiler.generated;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class Csc435Lexer extends Lexer {
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

	    @Override
	    public void recover(RecognitionException ex) {
	        throw new RuntimeException(ex);
	    }


	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public Csc435Lexer() {} 
	public Csc435Lexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public Csc435Lexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g"; }

	// $ANTLR start "OPEN_PAREN"
	public final void mOPEN_PAREN() throws RecognitionException {
		try {
			int _type = OPEN_PAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:228:12: ( '(' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:228:14: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OPEN_PAREN"

	// $ANTLR start "CLOSE_PAREN"
	public final void mCLOSE_PAREN() throws RecognitionException {
		try {
			int _type = CLOSE_PAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:230:13: ( ')' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:230:15: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CLOSE_PAREN"

	// $ANTLR start "OPEN_BRACE"
	public final void mOPEN_BRACE() throws RecognitionException {
		try {
			int _type = OPEN_BRACE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:232:12: ( '{' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:232:14: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OPEN_BRACE"

	// $ANTLR start "CLOSE_BRACE"
	public final void mCLOSE_BRACE() throws RecognitionException {
		try {
			int _type = CLOSE_BRACE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:234:13: ( '}' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:234:15: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CLOSE_BRACE"

	// $ANTLR start "OPEN_BRACKET"
	public final void mOPEN_BRACKET() throws RecognitionException {
		try {
			int _type = OPEN_BRACKET;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:236:14: ( '[' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:236:16: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OPEN_BRACKET"

	// $ANTLR start "CLOSE_BRACKET"
	public final void mCLOSE_BRACKET() throws RecognitionException {
		try {
			int _type = CLOSE_BRACKET;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:238:15: ( ']' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:238:17: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CLOSE_BRACKET"

	// $ANTLR start "COMMA"
	public final void mCOMMA() throws RecognitionException {
		try {
			int _type = COMMA;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:240:7: ( ',' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:240:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMA"

	// $ANTLR start "SEMICOLON"
	public final void mSEMICOLON() throws RecognitionException {
		try {
			int _type = SEMICOLON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:242:11: ( ';' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:242:13: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SEMICOLON"

	// $ANTLR start "EQUALS"
	public final void mEQUALS() throws RecognitionException {
		try {
			int _type = EQUALS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:244:8: ( '=' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:244:10: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EQUALS"

	// $ANTLR start "MULT_OP"
	public final void mMULT_OP() throws RecognitionException {
		try {
			int _type = MULT_OP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:246:9: ( '*' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:246:11: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MULT_OP"

	// $ANTLR start "ADD_OP"
	public final void mADD_OP() throws RecognitionException {
		try {
			int _type = ADD_OP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:248:8: ( '+' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:248:10: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ADD_OP"

	// $ANTLR start "SUB_OP"
	public final void mSUB_OP() throws RecognitionException {
		try {
			int _type = SUB_OP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:250:8: ( '-' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:250:10: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SUB_OP"

	// $ANTLR start "LESS_OP"
	public final void mLESS_OP() throws RecognitionException {
		try {
			int _type = LESS_OP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:252:9: ( '<' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:252:11: '<'
			{
			match('<'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LESS_OP"

	// $ANTLR start "EQUAL_OP"
	public final void mEQUAL_OP() throws RecognitionException {
		try {
			int _type = EQUAL_OP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:254:10: ( '==' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:254:12: '=='
			{
			match("=="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EQUAL_OP"

	// $ANTLR start "IF"
	public final void mIF() throws RecognitionException {
		try {
			int _type = IF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:256:4: ( 'if' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:256:6: 'if'
			{
			match("if"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IF"

	// $ANTLR start "ELSE"
	public final void mELSE() throws RecognitionException {
		try {
			int _type = ELSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:258:6: ( 'else' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:258:8: 'else'
			{
			match("else"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ELSE"

	// $ANTLR start "WHILE"
	public final void mWHILE() throws RecognitionException {
		try {
			int _type = WHILE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:260:7: ( 'while' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:260:9: 'while'
			{
			match("while"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WHILE"

	// $ANTLR start "PRINT"
	public final void mPRINT() throws RecognitionException {
		try {
			int _type = PRINT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:262:7: ( 'print' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:262:9: 'print'
			{
			match("print"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PRINT"

	// $ANTLR start "PRINTLN"
	public final void mPRINTLN() throws RecognitionException {
		try {
			int _type = PRINTLN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:264:9: ( 'println' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:264:11: 'println'
			{
			match("println"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PRINTLN"

	// $ANTLR start "RETURN"
	public final void mRETURN() throws RecognitionException {
		try {
			int _type = RETURN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:266:8: ( 'return' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:266:10: 'return'
			{
			match("return"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RETURN"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:268:5: ( 'int' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:268:7: 'int'
			{
			match("int"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "INT_CONSTANT"
	public final void mINT_CONSTANT() throws RecognitionException {
		try {
			int _type = INT_CONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:270:14: ( ( '0' .. '9' )+ )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:270:16: ( '0' .. '9' )+
			{
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:270:16: ( '0' .. '9' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT_CONSTANT"

	// $ANTLR start "FLOAT"
	public final void mFLOAT() throws RecognitionException {
		try {
			int _type = FLOAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:272:7: ( 'float' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:272:9: 'float'
			{
			match("float"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FLOAT"

	// $ANTLR start "FLOAT_CONSTANT"
	public final void mFLOAT_CONSTANT() throws RecognitionException {
		try {
			int _type = FLOAT_CONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:274:16: ( INT_CONSTANT '.' INT_CONSTANT )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:274:18: INT_CONSTANT '.' INT_CONSTANT
			{
			mINT_CONSTANT(); 

			match('.'); 
			mINT_CONSTANT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FLOAT_CONSTANT"

	// $ANTLR start "CHAR"
	public final void mCHAR() throws RecognitionException {
		try {
			int _type = CHAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:276:6: ( 'char' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:276:8: 'char'
			{
			match("char"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CHAR"

	// $ANTLR start "CHAR_CONSTANT"
	public final void mCHAR_CONSTANT() throws RecognitionException {
		try {
			int _type = CHAR_CONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:278:17: ( '\\'' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '!' | ',' | '.' | ':' | '_' | '{' | '}' | ' ' ) '\\'' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:279:21: '\\'' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '!' | ',' | '.' | ':' | '_' | '{' | '}' | ' ' ) '\\''
			{
			match('\''); 
			if ( (input.LA(1) >= ' ' && input.LA(1) <= '!')||input.LA(1)==','||input.LA(1)=='.'||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= '{')||input.LA(1)=='}' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			match('\''); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CHAR_CONSTANT"

	// $ANTLR start "STRING"
	public final void mSTRING() throws RecognitionException {
		try {
			int _type = STRING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:295:8: ( 'string' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:295:10: 'string'
			{
			match("string"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING"

	// $ANTLR start "STRING_CONSTANT"
	public final void mSTRING_CONSTANT() throws RecognitionException {
		try {
			int _type = STRING_CONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:297:17: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '!' | ',' | '.' | ':' | '_' | '{' | '}' | ' ' )* '\"' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:298:21: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '!' | ',' | '.' | ':' | '_' | '{' | '}' | ' ' )* '\"'
			{
			match('\"'); 
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:299:21: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '!' | ',' | '.' | ':' | '_' | '{' | '}' | ' ' )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= ' ' && LA2_0 <= '!')||LA2_0==','||LA2_0=='.'||(LA2_0 >= '0' && LA2_0 <= ':')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= '{')||LA2_0=='}') ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:
					{
					if ( (input.LA(1) >= ' ' && input.LA(1) <= '!')||input.LA(1)==','||input.LA(1)=='.'||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= '{')||input.LA(1)=='}' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING_CONSTANT"

	// $ANTLR start "BOOLEAN"
	public final void mBOOLEAN() throws RecognitionException {
		try {
			int _type = BOOLEAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:314:9: ( 'boolean' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:314:11: 'boolean'
			{
			match("boolean"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BOOLEAN"

	// $ANTLR start "BOOLEAN_CONSTANT"
	public final void mBOOLEAN_CONSTANT() throws RecognitionException {
		try {
			int _type = BOOLEAN_CONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:316:18: ( ( 'true' | 'false' ) )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:316:20: ( 'true' | 'false' )
			{
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:316:20: ( 'true' | 'false' )
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0=='t') ) {
				alt3=1;
			}
			else if ( (LA3_0=='f') ) {
				alt3=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:316:21: 'true'
					{
					match("true"); 

					}
					break;
				case 2 :
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:316:30: 'false'
					{
					match("false"); 

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BOOLEAN_CONSTANT"

	// $ANTLR start "VOID"
	public final void mVOID() throws RecognitionException {
		try {
			int _type = VOID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:318:6: ( 'void' )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:318:8: 'void'
			{
			match("void"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VOID"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:320:4: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:321:9: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:326:9: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '0' && LA4_0 <= '9')||(LA4_0 >= 'A' && LA4_0 <= 'Z')||LA4_0=='_'||(LA4_0 >= 'a' && LA4_0 <= 'z')) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop4;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:338:4: ( ( '\\t' | ' ' | ( '\\r' | '\\n' ) )+ )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:338:6: ( '\\t' | ' ' | ( '\\r' | '\\n' ) )+
			{
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:338:6: ( '\\t' | ' ' | ( '\\r' | '\\n' ) )+
			int cnt5=0;
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( ((LA5_0 >= '\t' && LA5_0 <= '\n')||LA5_0=='\r'||LA5_0==' ') ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt5 >= 1 ) break loop5;
					EarlyExitException eee = new EarlyExitException(5, input);
					throw eee;
				}
				cnt5++;
			}

			 _channel = HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:339:9: ( '//' (~ ( '\\r' | '\\n' ) )* ( '\\r' | '\\n' ) )
			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:339:11: '//' (~ ( '\\r' | '\\n' ) )* ( '\\r' | '\\n' )
			{
			match("//"); 

			// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:339:16: (~ ( '\\r' | '\\n' ) )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( ((LA6_0 >= '\u0000' && LA6_0 <= '\t')||(LA6_0 >= '\u000B' && LA6_0 <= '\f')||(LA6_0 >= '\u000E' && LA6_0 <= '\uFFFF')) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop6;
				}
			}

			if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			 _channel = HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	@Override
	public void mTokens() throws RecognitionException {
		// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:8: ( OPEN_PAREN | CLOSE_PAREN | OPEN_BRACE | CLOSE_BRACE | OPEN_BRACKET | CLOSE_BRACKET | COMMA | SEMICOLON | EQUALS | MULT_OP | ADD_OP | SUB_OP | LESS_OP | EQUAL_OP | IF | ELSE | WHILE | PRINT | PRINTLN | RETURN | INT | INT_CONSTANT | FLOAT | FLOAT_CONSTANT | CHAR | CHAR_CONSTANT | STRING | STRING_CONSTANT | BOOLEAN | BOOLEAN_CONSTANT | VOID | ID | WS | COMMENT )
		int alt7=34;
		alt7 = dfa7.predict(input);
		switch (alt7) {
			case 1 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:10: OPEN_PAREN
				{
				mOPEN_PAREN(); 

				}
				break;
			case 2 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:21: CLOSE_PAREN
				{
				mCLOSE_PAREN(); 

				}
				break;
			case 3 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:33: OPEN_BRACE
				{
				mOPEN_BRACE(); 

				}
				break;
			case 4 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:44: CLOSE_BRACE
				{
				mCLOSE_BRACE(); 

				}
				break;
			case 5 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:56: OPEN_BRACKET
				{
				mOPEN_BRACKET(); 

				}
				break;
			case 6 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:69: CLOSE_BRACKET
				{
				mCLOSE_BRACKET(); 

				}
				break;
			case 7 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:83: COMMA
				{
				mCOMMA(); 

				}
				break;
			case 8 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:89: SEMICOLON
				{
				mSEMICOLON(); 

				}
				break;
			case 9 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:99: EQUALS
				{
				mEQUALS(); 

				}
				break;
			case 10 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:106: MULT_OP
				{
				mMULT_OP(); 

				}
				break;
			case 11 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:114: ADD_OP
				{
				mADD_OP(); 

				}
				break;
			case 12 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:121: SUB_OP
				{
				mSUB_OP(); 

				}
				break;
			case 13 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:128: LESS_OP
				{
				mLESS_OP(); 

				}
				break;
			case 14 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:136: EQUAL_OP
				{
				mEQUAL_OP(); 

				}
				break;
			case 15 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:145: IF
				{
				mIF(); 

				}
				break;
			case 16 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:148: ELSE
				{
				mELSE(); 

				}
				break;
			case 17 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:153: WHILE
				{
				mWHILE(); 

				}
				break;
			case 18 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:159: PRINT
				{
				mPRINT(); 

				}
				break;
			case 19 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:165: PRINTLN
				{
				mPRINTLN(); 

				}
				break;
			case 20 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:173: RETURN
				{
				mRETURN(); 

				}
				break;
			case 21 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:180: INT
				{
				mINT(); 

				}
				break;
			case 22 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:184: INT_CONSTANT
				{
				mINT_CONSTANT(); 

				}
				break;
			case 23 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:197: FLOAT
				{
				mFLOAT(); 

				}
				break;
			case 24 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:203: FLOAT_CONSTANT
				{
				mFLOAT_CONSTANT(); 

				}
				break;
			case 25 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:218: CHAR
				{
				mCHAR(); 

				}
				break;
			case 26 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:223: CHAR_CONSTANT
				{
				mCHAR_CONSTANT(); 

				}
				break;
			case 27 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:237: STRING
				{
				mSTRING(); 

				}
				break;
			case 28 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:244: STRING_CONSTANT
				{
				mSTRING_CONSTANT(); 

				}
				break;
			case 29 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:260: BOOLEAN
				{
				mBOOLEAN(); 

				}
				break;
			case 30 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:268: BOOLEAN_CONSTANT
				{
				mBOOLEAN_CONSTANT(); 

				}
				break;
			case 31 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:285: VOID
				{
				mVOID(); 

				}
				break;
			case 32 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:290: ID
				{
				mID(); 

				}
				break;
			case 33 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:293: WS
				{
				mWS(); 

				}
				break;
			case 34 :
				// /home/daniel/docs/csc435/as1/src/main/antlr/Csc435.g:1:296: COMMENT
				{
				mCOMMENT(); 

				}
				break;

		}
	}


	protected DFA7 dfa7 = new DFA7(this);
	static final String DFA7_eotS =
		"\11\uffff\1\40\4\uffff\5\34\1\47\2\34\1\uffff\1\34\1\uffff\3\34\5\uffff"+
		"\1\60\5\34\2\uffff\7\34\1\uffff\1\75\13\34\1\uffff\1\111\5\34\1\117\2"+
		"\34\1\122\1\123\1\uffff\1\124\1\126\1\34\1\130\1\122\1\uffff\2\34\3\uffff"+
		"\1\34\1\uffff\1\134\1\uffff\1\135\1\34\1\137\2\uffff\1\140\2\uffff";
	static final String DFA7_eofS =
		"\141\uffff";
	static final String DFA7_minS =
		"\1\11\10\uffff\1\75\4\uffff\1\146\1\154\1\150\1\162\1\145\1\56\1\141\1"+
		"\150\1\uffff\1\164\1\uffff\1\157\1\162\1\157\5\uffff\1\60\1\164\1\163"+
		"\2\151\1\164\2\uffff\1\157\1\154\1\141\1\162\1\157\1\165\1\151\1\uffff"+
		"\1\60\1\145\1\154\1\156\1\165\1\141\1\163\1\162\1\151\1\154\1\145\1\144"+
		"\1\uffff\1\60\1\145\1\164\1\162\1\164\1\145\1\60\1\156\1\145\2\60\1\uffff"+
		"\2\60\1\156\2\60\1\uffff\1\147\1\141\3\uffff\1\156\1\uffff\1\60\1\uffff"+
		"\1\60\1\156\1\60\2\uffff\1\60\2\uffff";
	static final String DFA7_maxS =
		"\1\175\10\uffff\1\75\4\uffff\1\156\1\154\1\150\1\162\1\145\1\71\1\154"+
		"\1\150\1\uffff\1\164\1\uffff\1\157\1\162\1\157\5\uffff\1\172\1\164\1\163"+
		"\2\151\1\164\2\uffff\1\157\1\154\1\141\1\162\1\157\1\165\1\151\1\uffff"+
		"\1\172\1\145\1\154\1\156\1\165\1\141\1\163\1\162\1\151\1\154\1\145\1\144"+
		"\1\uffff\1\172\1\145\1\164\1\162\1\164\1\145\1\172\1\156\1\145\2\172\1"+
		"\uffff\2\172\1\156\2\172\1\uffff\1\147\1\141\3\uffff\1\156\1\uffff\1\172"+
		"\1\uffff\1\172\1\156\1\172\2\uffff\1\172\2\uffff";
	static final String DFA7_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\12\1\13\1\14\1\15"+
		"\10\uffff\1\32\1\uffff\1\34\3\uffff\1\40\1\41\1\42\1\16\1\11\6\uffff\1"+
		"\26\1\30\7\uffff\1\17\14\uffff\1\25\13\uffff\1\20\5\uffff\1\31\2\uffff"+
		"\1\36\1\37\1\21\1\uffff\1\22\1\uffff\1\27\3\uffff\1\24\1\33\1\uffff\1"+
		"\23\1\35";
	static final String DFA7_specialS =
		"\141\uffff}>";
	static final String[] DFA7_transitionS = {
			"\2\35\2\uffff\1\35\22\uffff\1\35\1\uffff\1\30\4\uffff\1\26\1\1\1\2\1"+
			"\12\1\13\1\7\1\14\1\uffff\1\36\12\23\1\uffff\1\10\1\15\1\11\3\uffff\32"+
			"\34\1\5\1\uffff\1\6\1\uffff\1\34\1\uffff\1\34\1\31\1\25\1\34\1\17\1\24"+
			"\2\34\1\16\6\34\1\21\1\34\1\22\1\27\1\32\1\34\1\33\1\20\3\34\1\3\1\uffff"+
			"\1\4",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\37",
			"",
			"",
			"",
			"",
			"\1\41\7\uffff\1\42",
			"\1\43",
			"\1\44",
			"\1\45",
			"\1\46",
			"\1\50\1\uffff\12\23",
			"\1\52\12\uffff\1\51",
			"\1\53",
			"",
			"\1\54",
			"",
			"\1\55",
			"\1\56",
			"\1\57",
			"",
			"",
			"",
			"",
			"",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"\1\61",
			"\1\62",
			"\1\63",
			"\1\64",
			"\1\65",
			"",
			"",
			"\1\66",
			"\1\67",
			"\1\70",
			"\1\71",
			"\1\72",
			"\1\73",
			"\1\74",
			"",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"\1\76",
			"\1\77",
			"\1\100",
			"\1\101",
			"\1\102",
			"\1\103",
			"\1\104",
			"\1\105",
			"\1\106",
			"\1\107",
			"\1\110",
			"",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"\1\112",
			"\1\113",
			"\1\114",
			"\1\115",
			"\1\116",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"\1\120",
			"\1\121",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\13\34\1\125\16\34",
			"\1\127",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"",
			"\1\131",
			"\1\132",
			"",
			"",
			"",
			"\1\133",
			"",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"\1\136",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"",
			"",
			"\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
			"",
			""
	};

	static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
	static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
	static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
	static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
	static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
	static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
	static final short[][] DFA7_transition;

	static {
		int numStates = DFA7_transitionS.length;
		DFA7_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
		}
	}

	protected class DFA7 extends DFA {

		public DFA7(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 7;
			this.eot = DFA7_eot;
			this.eof = DFA7_eof;
			this.min = DFA7_min;
			this.max = DFA7_max;
			this.accept = DFA7_accept;
			this.special = DFA7_special;
			this.transition = DFA7_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( OPEN_PAREN | CLOSE_PAREN | OPEN_BRACE | CLOSE_BRACE | OPEN_BRACKET | CLOSE_BRACKET | COMMA | SEMICOLON | EQUALS | MULT_OP | ADD_OP | SUB_OP | LESS_OP | EQUAL_OP | IF | ELSE | WHILE | PRINT | PRINTLN | RETURN | INT | INT_CONSTANT | FLOAT | FLOAT_CONSTANT | CHAR | CHAR_CONSTANT | STRING | STRING_CONSTANT | BOOLEAN | BOOLEAN_CONSTANT | VOID | ID | WS | COMMENT );";
		}
	}

}
