package services.crypto;


import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.util.Util;


//Cada letra é codificada para uma sequencia de caracteres. Essa sequencia pode ser de 3 ou 4 caracteres dependenndo se seu indice na string é impar ou par. (par -> 3 chars, impar -> 4 chars)


@Singleton

public class CryptoManager {


    //indices da string de entrada onde serão inseridos os caracteres da string de validação
    public static final int IDX_PRI_CHAR_STR_VALIDACAO = 1;
    public static final int IDX_SEG_CHAR_STR_VALIDACAO = 3;
    public static final int IDX_TER_CHAR_STR_VALIDACAO = 5;



    //em produção levar para variaveis locais pois ao decompilar é facil descobrir
    public static final List<String> LATIN_LOWER = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
    public static final List<String> LATIN_UPPER = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    public static final List<String> NUM = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    public static final List<String> SPECIAL_ACEITAVEIS_SAIDA = Arrays.asList("!", "#", "$", "*", "+", "-", "=", "?", "_", "{", "|", "}", ".");
    public static final List<String> SPECIAL = Arrays.asList("@", "%", "&", "'", "/", "^", "`", "~", " ", "\n", "\"", ":", "\t", "ç", "?", ";");             //'\n', '"' e ':' são importantes para jsons (quebra de linha, aspas duplas e dois pontos)
    //outros nao documentados no stackoverflow que pode para email, mas eu fiz para propositos gerais e evitar erros
    public static final List<String> OUTROS = Arrays.asList("(", ")", ",");


    //Caracteres possiveis para a string de validacao
    private static final List<String> charsPossiveisPriESegSV = new ArrayList<>();
    private static final List<String> POSSIVEIS_3O_CHAR_STRING_VALIDACAO = Arrays.asList("a", "b", "c", "u", "l", "k");


    /*Map<Character, String[]> mapCharEnc = new HashMap<>();          //mapa de caracteres para codificar enc-dec
    Map<String, Character> mapCharDec = new HashMap<>();            //mapad e caracteres para decodificar. É criado no momento que cria o map encode, apenas para nao usar um BiMap*/
    Map<String, String[]> mapCharEnc = new HashMap<>();          //mapa de caracteres para codificar enc-dec
    Map<String, String> mapCharDec = new HashMap<>();            //mapad e caracteres para decodificar. É criado no momento que cria o map encode, apenas para nao usar um BiMap

    public CryptoManager() {
        //Esses são os caracteres possiveis para os primeiros e segundos caracteres da string de validação sem ser a de login
        charsPossiveisPriESegSV.addAll(LATIN_LOWER);
        charsPossiveisPriESegSV.addAll(LATIN_UPPER);
        charsPossiveisPriESegSV.addAll(NUM);

//debug tenta dar conflito de strs randomicas
       /* Util.hideLog = true;
        for (int i = 0; i < 1000; i++) {
            gerarRandomMapChar();
        }
        Util.hideLog = false;*/

        inicializarMapCharFixo();
        //gerarRandomMapChar();

        printMapChar();

        printSourceCodeMapChar();
    }


    //map char resultado de uma chamada a gerarRandomMapChar(), esse é o map versão 1, e é replicado no servidor
    private void inicializarMapCharFixo() {
        addMapChar("\t", "-kD", "k6pe");
        addMapChar("\n", "mds", "+tI7");
        addMapChar(" ", "D$z", "6p*M");
        addMapChar("!", "1Zj", "!Qbs");
        addMapChar("\"", "KpO", "nqvS");
        addMapChar("#", "=?#", "V*Bs");
        addMapChar("$", "ld6", "SnOk");
        addMapChar("%", "x*X", "7o#8");
        addMapChar("&", "0ZL", "cRca");
        addMapChar("'", "gy.", "H4eG");
        addMapChar("(", "tc.", "pfRD");
        addMapChar(")", "4Nz", "58N$");
        addMapChar("*", "JPW", "F}Df");
        addMapChar("+", "Ell", "wQaZ");
        addMapChar(",", "qOz", "N0CJ");
        addMapChar("-", "SiV", "$Gep");
        addMapChar(".", "7ms", "Yr{w");
        addMapChar("/", "HZv", "1}ev");
        addMapChar("0", "8FI", "GOpC");
        addMapChar("1", "oYO", "m8Ca");
        addMapChar("2", "z#r", "Cu}N");
        addMapChar("3", "q!t", ".S3+");
        addMapChar("4", "MI{", "e6W6");
        addMapChar("5", "4bL", "-{VQ");
        addMapChar("6", "R7f", "w40P");
        addMapChar("7", "!Pd", "M.xU");
        addMapChar("8", "uHa", "oqQg");
        addMapChar("9", "1GR", "pj57");
        addMapChar(":", "hPQ", "HxST");
        addMapChar(";", "oHo", "Vcfu");
        addMapChar("=", "Va0", "kJXQ");
        addMapChar("?", "PV-", "W4QE");
        addMapChar("@", "mE_", "*|N9");
        addMapChar("A", "d*y", "IVpQ");
        addMapChar("B", "=|l", "JFPx");
        addMapChar("C", "y32", "xHXa");
        addMapChar("D", "QTv", ".ndN");
        addMapChar("E", "BGW", "K-F5");
        addMapChar("F", "#zX", "$X7i");
        addMapChar("G", "XIf", "kJ2u");
        addMapChar("H", "amn", "aroH");
        addMapChar("I", "q!_", "a!ON");
        addMapChar("J", "4l3", "u?kz");
        addMapChar("K", "fxB", "2*iJ");
        addMapChar("L", "587", "VV#o");
        addMapChar("M", "Izu", "KVQ-");
        addMapChar("N", "Hz3", "zblJ");
        addMapChar("O", "$!9", "Ej$V");
        addMapChar("P", "i3-", "AT2r");
        addMapChar("Q", "+CU", "sfzc");
        addMapChar("R", "8ja", "sI?m");
        addMapChar("S", "Itr", "4jhO");
        addMapChar("T", "3aM", "3cJc");
        addMapChar("U", "LYi", "P{jG");
        addMapChar("V", "bRx", "ov#A");
        addMapChar("W", "J9o", "AgGi");
        addMapChar("X", "s3$", "#FEk");
        addMapChar("Y", "SLX", "#8!I");
        addMapChar("Z", "s4*", "xmDy");
        addMapChar("^", "N4A", "Tz_w");
        addMapChar("_", "OgH", "z_mM");
        addMapChar("`", "4EE", "js?9");
        addMapChar("a", "!QZ", "lPob");
        addMapChar("b", "bxT", "qPz4");
        addMapChar("c", "CZ7", "AA|9");
        addMapChar("d", "qYm", "6Hy#");
        addMapChar("e", "IE|", "3YWz");
        addMapChar("f", "tjw", "akgi");
        addMapChar("g", "_nm", "}KcE");
        addMapChar("ç", "DMI", "abVo");
        addMapChar("h", "XMn", "ScI7");
        addMapChar("i", "|9B", "M_Zs");
        addMapChar("j", "T$Q", "Q+t2");
        addMapChar("k", "_II", "BS=n");
        addMapChar("l", "|ZD", "xd=J");
        addMapChar("m", "E4{", "Ky1r");
        addMapChar("n", "a4g", "wWi=");
        addMapChar("o", "u}l", "+uUj");
        addMapChar("p", "_9A", "{f.r");
        addMapChar("q", "t7Z", "7iyM");
        addMapChar("r", "TVt", "cbxY");
        addMapChar("s", "|i*", "MBk}");
        addMapChar("t", "c5*", "Lr}d");
        addMapChar("u", "V}5", "}5i3");
        addMapChar("v", "bmX", "AT#K");
        addMapChar("w", "a_*", "fXmd");
        addMapChar("x", "-qm", "oKPF");
        addMapChar("y", "$9|", "3QGr");
        addMapChar("z", "$s!", "dfaO");
        addMapChar("{", "DW?", "$vVX");
        addMapChar("|", "JCG", "+KjL");
        addMapChar("}", "yyb", "v#|j");
        addMapChar("~", "{yc", "vJgk");
    }

    static final int TAM_STR_ENC_PAR = 3;
    static final int TAM_STR_ENC_IMPAR = 4;


    //caracteres possiveis para o 3o char da string de validação
    public void gerarRandomMapChar() {
        //Possiveis caracteres para nomes de email (segundo stackoverflow)

        //Como entrada é aceito todos os caracteres possiveis
        List<String> charsEntrada = new ArrayList<>();
        charsEntrada.addAll(LATIN_LOWER);
        charsEntrada.addAll(LATIN_UPPER);
        charsEntrada.addAll(NUM);
        charsEntrada.addAll(SPECIAL_ACEITAVEIS_SAIDA);
        charsEntrada.addAll(SPECIAL);
        charsEntrada.addAll(OUTROS);

        //Como saida (string codificada) eu restringi para apenas letras e numeros, evitando caracteres especiais, para nao dar bronca no charset das requisicoes
        List<String> charsEncoded = new ArrayList<>();
        charsEncoded.addAll(LATIN_LOWER);
        charsEncoded.addAll(LATIN_UPPER);
        charsEncoded.addAll(NUM);
        charsEncoded.addAll(SPECIAL_ACEITAVEIS_SAIDA);

//        int idxParImpar = 0;

        //Para todoo caractere, gera as strings de 3 e 4 caracteres
        for (String c : charsEntrada) {
            //String printC = c;          //usa isso para o print no log

            String tresChars = Util.gerarStrRandom(charsEncoded, TAM_STR_ENC_PAR);
            String quatroChars = Util.gerarStrRandom(charsEncoded, TAM_STR_ENC_IMPAR);

            Util.log("c: " + c + " --> " + Arrays.toString(new String[]{tresChars, quatroChars}));


            //NÃO DEVE USAR ISSO
            addMapChar(c, tresChars, quatroChars);
        }
    }


    public String enconde(String str) throws Exception {
        Util.log("encode() str: " + str);

        StringBuilder sb = new StringBuilder();

        for (int i = 0, n = str.length(); i < n; i++) {
            //String[] encs = mapCharEnc.get(str.charAt(i));
            //Util.log("str.charAt(i): " + str.charAt(i));
            String c = Character.toString(str.charAt(i));
            String[] encs = mapCharEnc.get(c);
            if (encs == null) {
                Util.log("ERRO EXTREMO, encs == null, caractere c: " + c + ", nao tem mapeamento");
            }
            String strEnc = encs[i % 2 == 0 ? 0 : 1];
            //sb.append(mapCharEnc.get(str.charAt(i))[i % 2 == 0 ? 0 : 1]);        //linha curta
            sb.append(strEnc);
        }
        String res = sb.toString();
        Util.log("res: " + res);
        return res;
    }

    public String decode(String strEnc) throws Exception {
        Util.log("decode() strEnc: " + strEnc);

        StringBuilder sb = new StringBuilder();

        int i = 0;
        int charJaDecodedCount = 0;     //apenas para nao usar stringbuilder.lenght()
        String c;
        while (i < strEnc.length()) {
            String keyEnc;

            //Se o indice for par, pega 3 caracteres, se for impar, pega 4
            //int tamStrEnc = i % 2 == 0 ? 3 : 4;
            int tamStrEnc = charJaDecodedCount % 2 == 0 ? 3 : 4;
            keyEnc = strEnc.substring(i, i + tamStrEnc);
            c = mapCharDec.get(keyEnc);
            //Util.log("i: " + i + ", charJaDecodedCount: " + charJaDecodedCount + ", tamStrEnc: " + tamStrEnc + ", keyEnc: " + keyEnc + ", c: " + c);
            i += tamStrEnc;
            charJaDecodedCount++;

            sb.append(c);
        }

        String res = sb.toString();
        Util.log("res: " + res);
        return res;
    }

    private void addMapChar(String c, String... tresEQuatroChars) {
        Util.log("addMapChar() c: " + c + ", tresEQuatroChars: " + Arrays.toString(tresEQuatroChars));
        //Faz algumas verificações apenas para helper. Acho que isso deveria sair do codigo final para evitar descobrirem como faço
        if (tresEQuatroChars.length != 2) {
            Util.lancarExcecao("ERRO: tresEQuatroChars.length != 2");
        } else if (tresEQuatroChars[0].length() != 3) {
            Util.lancarExcecao("ERRO: tresEQuatroChars[0].length() != 3");
        } else if (tresEQuatroChars[1].length() != 4) {
            Util.lancarExcecao("ERRO: tresEQuatroChars[1].length() != 4");
        }

        //Preenche o map de codificacao
        mapCharEnc.put(c, tresEQuatroChars);

        //Preenche o map de decodificação criando uma chave para cada value. Apenas o map reverso
        for (String s : tresEQuatroChars) {
            mapCharDec.put(s, c);
        }

        if (temRepeticaoMapChar()) {
            Util.lancarExcecao("ERRO: tem repetição nos values do mapCharEnc");
        }
    }


    //Verifica se tem alguma sequencia de chars repetidas
    private boolean temRepeticaoMapChar() {
        List<String> repetidos = new ArrayList<>();
        List<String> lValues = new ArrayList<>();
        for (String[] sa : mapCharEnc.values()) {
            for (String s : sa) {
                if (lValues.contains(s)) {       //adiciona aos repetidos para poder ao final saber
                    repetidos.add(s);
                }

                lValues.add(s);
            }
        }

        boolean res = !repetidos.isEmpty();

        //apenas para forçar logar se der erro
        if (res) {
            boolean oldHideLogs = Util.hideLog;
            Util.hideLog = false;
            Util.log("checarRepeticaoMapChar() repetidos.size() " + repetidos.size() + ", repetidos: " + Arrays.toString(repetidos.toArray()));
            Util.hideLog = oldHideLogs;
        }

        //correto
        Util.log("checarRepeticaoMapChar() repetidos.size() " + repetidos.size() + ", repetidos: " + Arrays.toString(repetidos.toArray()));

        return res;
    }

    void printMapChar() {
        Util.log("###################### PRINT MAP CHAR ENC ############################");
        for (Map.Entry<String, String[]> e : mapCharEnc.entrySet()) {
            Util.log(e.getKey() + " --> " + Arrays.toString(e.getValue()));
        }
        Util.log("###################### FIM - PRINT MAP CHAR ENC ############################");
    }

    void printSourceCodeMapChar() {
        StringBuilder sbSource = new StringBuilder();
        for (Map.Entry<String, String[]> e : mapCharEnc.entrySet()) {
            //Util.log(e.getKey() + " --> " + Arrays.toString(e.getValue()));

            String keyPrint = e.getKey();
            if (keyPrint.equals("\n")) {
                keyPrint = "SUBSTITUIR_POR_BREAKLINKE";
            } else if (keyPrint.equals("\t")) {
                keyPrint = "SUBSTITUIR_POR_TAB";
            } else if (keyPrint.equals("\"")) {
                keyPrint = "SUBSTITUIR_POR_ASPAS_DUPLAS";
            }


            sbSource.append("addMapChar(\"" + keyPrint + "\", \"" + e.getValue()[0] + "\", \"" + e.getValue()[1] + "\");");
            sbSource.append("\n");
        }

        Util.log("java code final:");
        Util.log(sbSource.toString());


        //addMapChar(',', "mOO", "MMMM");
    }

    public String aplicarStringValidacao(String entrada, String strValidacao){
        Util.log("aplicarStringValidacao() entrada: " + entrada + ", strValidacao: " + strValidacao);
        StringBuilder sb = new StringBuilder(entrada);
        sb.insert(IDX_PRI_CHAR_STR_VALIDACAO, strValidacao.charAt(0));
        sb.insert(IDX_SEG_CHAR_STR_VALIDACAO, strValidacao.charAt(1));
        sb.insert(IDX_TER_CHAR_STR_VALIDACAO, strValidacao.charAt(2));

        String res = sb.toString();
        Util.log("res: " + res);

        return sb.toString();


       /* String res = entrada.substring(0,1) + strValidacao.substring(0,1) + entrada.substring(1,2) + strValidacao.substring(1,2)+
                entrada.substring(2,3) + Util.getRandomElement(POSSIVEIS_3O_CHAR_STRING_VALIDACAO) + entrada.substring(3);
        Util.log("res: " + res);
        return res;*/
    }


    //Este método avalia se a sintaxe de uma string de validação está correta
    public boolean validarSintaxeStringValidacao(String stringValidacao){
        Util.log("validarSintaxeStringValidacao() stringValidacao: " + stringValidacao);
        if (stringValidacao.length() != 3){
            Util.log("ERRO: stringValidacao.length() != 3");
            return false;
        }

        //Verifica se o 3o caractere pertence a lista de possiveis chars
        if (!POSSIVEIS_3O_CHAR_STRING_VALIDACAO.contains(Character.toString(stringValidacao.charAt(2)))){
            Util.log("ERRO: 3o char da stringValidacao não está no array de POSSIVEIS_3O_CHAR_STRING_VALIDACAO, ele é: " + stringValidacao.charAt(2));
            return false;
        }

        Util.log("res: true");
        return true;
    }

    //Essa verificação toma uma string de entrada aplicada a uma string de validação, e uma outra string de validação.
    //Ela apenas compara se a string de validação bate com a string de entrada, não valida se a sitaxe da string de validação está correta
    public boolean confrontarComStringValidacao(String entrada, String stringValidacao){
        Util.log("confrontarComStringValidacao() entrada: " + entrada + ", stringValidacao: " + stringValidacao);

        if (entrada.charAt(IDX_PRI_CHAR_STR_VALIDACAO) != stringValidacao.charAt(0)){
            Util.log("ERRO: entrada.charAt(IDX_PRI_CHAR_STR_VALIDACAO) != stringValidacao.charAt(0)");
            return false;
        }
        else  if (entrada.charAt(IDX_SEG_CHAR_STR_VALIDACAO) != stringValidacao.charAt(1)){
            Util.log("ERRO: entrada.charAt(IDX_SEG_CHAR_STR_VALIDACAO) != stringValidacao.charAt(1)");
            return false;
        }
        else  if (entrada.charAt(IDX_TER_CHAR_STR_VALIDACAO) != stringValidacao.charAt(2)){
            Util.log("ERRO: entrada.charAt(IDX_TER_CHAR_STR_VALIDACAO) != stringValidacao.charAt(2)");
            return false;
        }

        Util.log("res: true");
        return true;
    }



    //Remove os tres caracteres da string de validação da entrada. Não valida se a sitaxe da string de validação está correta, e espera que venha sem erros
    public String removerStringValidacao(String entradaComSV){
        Util.log("removerStringValidacao() entradaComSV: " + entradaComSV);

        //remove a string de validação
        StringBuilder sb = new StringBuilder(entradaComSV);
        //deleta na ordem inversa pra manter os indices, pois se deletasse primeiro o indice 1, então o proximo nao estaria mais no indice 3, mas sim no indice 2 pois diminuiu o tamanho da string
        sb.deleteCharAt(IDX_TER_CHAR_STR_VALIDACAO);
        sb.deleteCharAt(IDX_SEG_CHAR_STR_VALIDACAO);
        sb.deleteCharAt(IDX_PRI_CHAR_STR_VALIDACAO);

        String res = sb.toString();
        Util.log("res: " + res);
        return res;
    }

    //Extrai a string de validação das posiçoes de uma entrada. Não valida a sintaxe
    public String extrairStringValidacao(String entradaComSV){
        Util.log("extrairStringValidacao() entradaComSV: " + entradaComSV);

        String res = Character.toString(entradaComSV.charAt(IDX_PRI_CHAR_STR_VALIDACAO)) +
                Character.toString(entradaComSV.charAt(IDX_SEG_CHAR_STR_VALIDACAO)) +
                Character.toString(entradaComSV.charAt(IDX_TER_CHAR_STR_VALIDACAO));

        Util.log("res: " + res);
        return res;
    }
    /*
    //Extrai a string de validação das posiçoes de uma entrada. Não valida a sintaxe
    public String extrairStringValidacao(String entradaComSV){
        Util.log("extrairStringValidacao() entradaComSV: " + entradaComSV);

        //remove a string de validação
        StringBuilder sb = new StringBuilder();
        sb.append(entradaComSV.charAt(IDX_PRI_CHAR_STR_VALIDACAO));
        sb.append(entradaComSV.charAt(IDX_SEG_CHAR_STR_VALIDACAO));
        sb.append(entradaComSV.charAt(IDX_TER_CHAR_STR_VALIDACAO));

        String res = sb.toString();
        Util.log("res: " + res);
        return res;
    }
*/

    //É usado somente no login, pois essa string de validação é gerada através do id. Está descrito em "login e flow.txt"
   /*- O 1o caractere é a quantidade de caracteres da string do id, sem a string de validação e ao final pega o ultimo caractere do size() (para poder
    ter apenas um caractere) ou seja, id.size() - 3. Note que -3 pois embora a string de validação contenha 2 caracteres, um ultimo caractere é gerado a partir
    de uma lista predefinida, como esplicado na sessão "STRING DE VALIDAÇÃO".
     - O 2o caractere é o numero completo do primeiro caractere multiplicado por 3 e pega o ultimo caractere do numero resultante
	- O 3o caractere é igual como nas outras strings de validação e está descrita na sessão "STRING DE VALIDAÇÃO"*/
    public String gerarStringValidacaoFromString(String id){
        String numStr = Integer.toString(id.length());
        String primeiro = Character.toString(numStr.charAt(numStr.length() - 1));

        String numStrSeg = Integer.toString(id.length() * 3);
        String segundo = Character.toString(numStrSeg.charAt(numStrSeg.length() - 1));

        String terceiro = gerarTerceiroCharStringValidacao();

        String res = primeiro + segundo + terceiro;
        Util.log("gerarStringValidacaoLogin() id: " + id + ", numStr: " + numStr + ", primeiro: " + primeiro + ", numStrSeg: " + numStrSeg + ", segundo: " + segundo + ", terceiro: "+ terceiro + ", res: " + res);

        return res;
    }


    //Aplica o algoritmo de geração de string de validação a partir de uma entrada para ver se a string de validação enviada é a mesma.
    public boolean validarSVGerada(String entrada, String SVExtraida){
        Util.log("validarSVGerada() entrada: " + entrada + ", SVExtraida: " + SVExtraida);

        //gera uma string de validação valida a partir da entrada
        String SVGeradaEntrada = gerarStringValidacaoFromString(entrada);
        Util.log("SVGeradaEntrada: " + SVGeradaEntrada);

        //Verifica se as strings de validação são iguais. Note que somente compara o char idx 0 e 1, pois o idx 2 é gerado aleatoriamente e já foi validado na sintaxe
        boolean res = (SVGeradaEntrada.charAt(0) == SVExtraida.charAt(0)) && (SVGeradaEntrada.charAt(1) == SVExtraida.charAt(1));
        Util.log("res: " + res);
        return res;
    }
    //É usado como retorno do login
    public String gerarStringValidacaoAleatoria(){
        String res= gerarPrimeiroESegundoCharStringValidacao() + gerarTerceiroCharStringValidacao();
        Util.log("gerarPrimeiroESegundoCharStringValidacao() res: " + res);
        return res;
    }


    private String gerarPrimeiroESegundoCharStringValidacao(){
        return Util.getRandomElement(charsPossiveisPriESegSV) +
                Util.getRandomElement(charsPossiveisPriESegSV);
    }

    private String gerarTerceiroCharStringValidacao(){
        return Util.getRandomElement(POSSIVEIS_3O_CHAR_STRING_VALIDACAO);
    }
}
