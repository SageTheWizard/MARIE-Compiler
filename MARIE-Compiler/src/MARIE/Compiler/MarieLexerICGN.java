package MARIE.Compiler;/* The following code was generated by JFlex 1.6.1 */

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.1
 * from the specification file <tt>MarieLexerICGN.flex</tt>
 */
class MarieLexerICGN {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\10\0\1\47\1\5\1\2\2\0\1\5\22\0\1\5\1\44\3\0"+
    "\1\41\1\45\1\0\1\31\1\32\1\40\1\36\1\26\1\37\1\0"+
    "\1\3\12\1\1\0\1\25\1\42\1\35\1\43\2\0\32\4\1\33"+
    "\1\0\1\34\1\0\1\4\1\0\1\24\1\13\2\4\1\20\1\21"+
    "\1\4\1\17\1\10\2\4\1\15\1\4\1\11\1\14\1\6\1\4"+
    "\1\7\1\22\1\12\1\23\1\4\1\16\3\4\1\27\1\46\1\30"+
    "\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uff92\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\10\5"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26"+
    "\2\1\1\27\1\30\3\5\1\31\5\5\1\32\1\33"+
    "\1\34\1\35\1\36\1\37\2\5\1\40\7\5\1\41"+
    "\1\42\1\5\1\43\1\44\1\5\1\45\1\46";

  private static int [] zzUnpackAction() {
    int [] result = new int[68];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\50\0\120\0\50\0\170\0\240\0\310\0\360"+
    "\0\u0118\0\u0140\0\u0168\0\u0190\0\u01b8\0\u01e0\0\u0208\0\50"+
    "\0\50\0\50\0\50\0\50\0\50\0\50\0\50\0\u0230"+
    "\0\50\0\50\0\50\0\50\0\u0258\0\u0280\0\u02a8\0\u02d0"+
    "\0\u02f8\0\50\0\50\0\u0320\0\u0348\0\u0370\0\240\0\u0398"+
    "\0\u03c0\0\u03e8\0\u0410\0\u0438\0\50\0\50\0\50\0\50"+
    "\0\50\0\50\0\u0460\0\u0488\0\240\0\u04b0\0\u04d8\0\u0500"+
    "\0\u0528\0\u0550\0\u0578\0\u05a0\0\240\0\240\0\u05c8\0\240"+
    "\0\240\0\u05f0\0\240\0\240";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[68];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\12\1\6\1\13\1\14\2\6\1\15\1\6\1\16"+
    "\1\17\3\6\1\20\1\21\1\22\1\23\1\24\1\25"+
    "\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35"+
    "\1\36\1\37\1\40\1\41\1\42\51\0\1\3\51\0"+
    "\1\43\45\0\1\6\2\0\1\6\1\0\17\6\30\0"+
    "\1\7\43\0\1\6\2\0\1\6\1\0\1\6\1\44"+
    "\15\6\24\0\1\6\2\0\1\6\1\0\12\6\1\45"+
    "\4\6\24\0\1\6\2\0\1\6\1\0\3\6\1\46"+
    "\7\6\1\47\3\6\24\0\1\6\2\0\1\6\1\0"+
    "\1\6\1\50\15\6\24\0\1\6\2\0\1\6\1\0"+
    "\6\6\1\51\10\6\24\0\1\6\2\0\1\6\1\0"+
    "\11\6\1\52\5\6\24\0\1\6\2\0\1\6\1\0"+
    "\7\6\1\53\7\6\24\0\1\6\2\0\1\6\1\0"+
    "\16\6\1\54\60\0\1\55\47\0\1\56\47\0\1\57"+
    "\47\0\1\60\57\0\1\61\50\0\1\62\2\0\1\6"+
    "\2\0\1\6\1\0\2\6\1\63\14\6\24\0\1\6"+
    "\2\0\1\6\1\0\4\6\1\64\12\6\24\0\1\6"+
    "\2\0\1\6\1\0\4\6\1\65\12\6\24\0\1\6"+
    "\2\0\1\6\1\0\15\6\1\66\1\6\24\0\1\6"+
    "\2\0\1\6\1\0\6\6\1\67\10\6\24\0\1\6"+
    "\2\0\1\6\1\0\2\6\1\70\14\6\24\0\1\6"+
    "\2\0\1\6\1\0\14\6\1\71\2\6\24\0\1\6"+
    "\2\0\1\6\1\0\7\6\1\72\7\6\24\0\1\6"+
    "\2\0\1\6\1\0\3\6\1\73\13\6\24\0\1\6"+
    "\2\0\1\6\1\0\15\6\1\74\1\6\24\0\1\6"+
    "\2\0\1\6\1\0\12\6\1\75\4\6\24\0\1\6"+
    "\2\0\1\6\1\0\7\6\1\76\7\6\24\0\1\6"+
    "\2\0\1\6\1\0\7\6\1\77\7\6\24\0\1\6"+
    "\2\0\1\6\1\0\12\6\1\100\4\6\24\0\1\6"+
    "\2\0\1\6\1\0\14\6\1\66\2\6\24\0\1\6"+
    "\2\0\1\6\1\0\4\6\1\101\12\6\24\0\1\6"+
    "\2\0\1\6\1\0\1\6\1\102\15\6\24\0\1\6"+
    "\2\0\1\6\1\0\12\6\1\103\4\6\24\0\1\6"+
    "\2\0\1\6\1\0\3\6\1\104\13\6\23\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1560];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\1\11\13\1\10\11\1\1\4\11"+
    "\5\1\2\11\11\1\6\11\22\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[68];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
    public ParserICGN parser;
	public int lineno;
	public String lastIdent;

    public MarieLexerICGN(java.io.Reader r, ParserICGN parser) {
        this(r);
        this.parser = parser;
		this.lineno = 1;
		this.lastIdent = "";
    }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  MarieLexerICGN(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 154) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return 0; }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { System.err.println("Unexpected Character: " + yytext()); return -1;
            }
          case 39: break;
          case 2: 
            { parser.yylval = new ParserVal((Object) yytext()); return ParserICGN.INT_LIT;
            }
          case 40: break;
          case 3: 
            { this.lineno++;
            }
          case 41: break;
          case 4: 
            { return ParserICGN.DIV;
            }
          case 42: break;
          case 5: 
            { this.lastIdent = yytext(); parser.yylval = new ParserVal((Object) this.lastIdent); return ParserICGN.IDENT;
            }
          case 43: break;
          case 6: 
            {
              //empty;
            }
          case 44: break;
          case 7: 
            { return ParserICGN.SEMI;
            }
          case 45: break;
          case 8: 
            { return ParserICGN.COMMA;
            }
          case 46: break;
          case 9: 
            { return ParserICGN.LCURLY;
            }
          case 47: break;
          case 10: 
            { return ParserICGN.RCURLY;
            }
          case 48: break;
          case 11: 
            { return ParserICGN.LCIRCLE;
            }
          case 49: break;
          case 12: 
            { return ParserICGN.RCIRCLE;
            }
          case 50: break;
          case 13: 
            { return ParserICGN.LSQUARE;
            }
          case 51: break;
          case 14: 
            { return ParserICGN.RSQUARE;
            }
          case 52: break;
          case 15: 
            { return ParserICGN.ASSIGN;
            }
          case 53: break;
          case 16: 
            { return ParserICGN.ADD;
            }
          case 54: break;
          case 17: 
            { return ParserICGN.SUB;
            }
          case 55: break;
          case 18: 
            { return ParserICGN.MUL;
            }
          case 56: break;
          case 19: 
            { return ParserICGN.MOD;
            }
          case 57: break;
          case 20: 
            { return ParserICGN.LT;
            }
          case 58: break;
          case 21: 
            { return ParserICGN.GT;
            }
          case 59: break;
          case 22: 
            { return ParserICGN.NOT;
            }
          case 60: break;
          case 23: 
            { System.err.println("BACKSPACE ERROR!");
            }
          case 61: break;
          case 24: 
            { System.out.println("Comment Detected: Ignoring");
            }
          case 62: break;
          case 25: 
            { return ParserICGN.IF;
            }
          case 63: break;
          case 26: 
            { return ParserICGN.EQ;
            }
          case 64: break;
          case 27: 
            { return ParserICGN.LE;
            }
          case 65: break;
          case 28: 
            { return ParserICGN.GE;
            }
          case 66: break;
          case 29: 
            { return ParserICGN.NE;
            }
          case 67: break;
          case 30: 
            { return ParserICGN.AND;
            }
          case 68: break;
          case 31: 
            { return ParserICGN.OR;
            }
          case 69: break;
          case 32: 
            { return ParserICGN.INT;
            }
          case 70: break;
          case 33: 
            { return ParserICGN.BOOL_LIT;
            }
          case 71: break;
          case 34: 
            { return ParserICGN.BOOL;
            }
          case 72: break;
          case 35: 
            { return ParserICGN.ELSE;
            }
          case 73: break;
          case 36: 
            { return ParserICGN.PRINT;
            }
          case 74: break;
          case 37: 
            { return ParserICGN.WHILE;
            }
          case 75: break;
          case 38: 
            { return ParserICGN.RETURN;
            }
          case 76: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
