package services.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import controllers.HomeController;

public class Util {

    public static String toJson(Object o) {
        return toJson(o, null, null);
    }

    //um serializer opcional
    public static String toJson(Object o, Type typeObjectSerializer, Object serializer) {
        GsonBuilder builder = new GsonBuilder();
        if (typeObjectSerializer != null) {
            builder.registerTypeAdapter(typeObjectSerializer, serializer);
        }
        builder.setPrettyPrinting();
        builder.disableHtmlEscaping();
        Gson gson = builder.create();
        return gson.toJson(o);
    }

    public static boolean hideLog = false;
    public static boolean hideLogProd = false;      //logs de producao são importantes e nunca devem ser ocultos

    //private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("application");

    static int MAX_SIZE_TAG = 30;
    public static String getSpaces(int spaces) {
        return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
    }

    public static void logProd(String msg){
        if (hideLogProd) {
            return;
        }

        logForce(msg);
    }
    public static void log(String msg) {
        if (hideLog) {
            return;
        }

        logForce(msg);
    }
    private static void logForce(String msg){
        try {
            String callerCallerClassName = KDebug.getCallerCallerClassName();

            if (callerCallerClassName.length() < MAX_SIZE_TAG) {  //acresencta espaços
                //caractere invisivel, apenas pro logcat nao truncar
                callerCallerClassName = callerCallerClassName +
                        getSpaces(MAX_SIZE_TAG - callerCallerClassName.length())
                        /*+ "\u200e"*/;
            }
            System.out.println(callerCallerClassName + msg);
        } catch (Exception e) {
            System.out.println("TAG nao encontrada" + msg);
        }
    }

    public static void deletarTodosArquivosPasta(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for(File file: files) {
            if (!file.isDirectory()) {      //nao apaga diretorios pois as vezes crio diretorios como backups
                file.delete();
            }
        }
    }

    static class KDebug {
        static String getCallerCallerClassName() {
            StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
            String callerClassName = null;
            for (int i = 1; i < stElements.length; i++) {
                StackTraceElement ste = stElements[i];
                if (!ste.getClassName().equals(KDebug.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                    if (callerClassName == null) {
                        callerClassName = ste.getClassName();
                    } else if (!callerClassName.equals(ste.getClassName())) {
                        //return ste.getClass().getSimpleName();
                        String fullClassName = ste.getClassName();
                        String simpleName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
                        //agora remove os possiveis $ de anonymous inner classes
                        int idxDolar = simpleName.indexOf("$");
                        if (idxDolar != -1) {
                            simpleName = simpleName.substring(0, idxDolar);
                        }


                        return simpleName;

                        //return ste.getClassName();
                    }
                }
            }
            return null;
        }
    }


    public static String removeLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }

    //Interface utilitaria para usos gerais quando quer carregar um objeto, por exemplo
    public interface OnObjectListener<T> {
        void onObject(T object);
    }
    public static void saveToFile(String fileNameAbsoulte, String conteudoFile) {
        try {
            FileOutputStream strm = new FileOutputStream(fileNameAbsoulte);
            System.out.println("fileNameAbsoulte: " + fileNameAbsoulte);
            strm.write(conteudoFile.getBytes());
            strm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readFroFile(String fileNameAbsoulte) {
        String res = null;
        try {
            FileInputStream fis = new FileInputStream(new File(fileNameAbsoulte));
            FileChannel fc = fis.getChannel();
            ByteBuffer bbSeq = ByteBuffer.allocate((int) (fc.size()));
            fc.read(bbSeq);
            res = new String(bbSeq.array());
            fc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public enum TipoNotacao {
        C, DO;
        //NAO_VIBRAR, PULSAR, TODO_TEMPO;

        //Obtém to tipo da vibração baseado na opção escolhida na lista de preferencia das opções, baseado no value para esta entrada.
        //valorEntradaLista o valor para uma entrada, ex: para NAOVIBRAR é: naoVibrarBaixos, naoVibrarTeclado (faz um ||)
        public static TipoNotacao parse(String valorEntradaLista/*, Resources res*/) {            //retorna o tipo baseado no value
            TipoNotacao tipoNotacao;
            if (valorEntradaLista.equals("notacaoC")) {    //Se for valor para N�O VIBRA��O
                tipoNotacao = TipoNotacao.C;
            } else {
                tipoNotacao = TipoNotacao.DO;
            }

            return tipoNotacao;
        }
    }


    public static String[] TECLAS_EXIBIR_NOTAS_NOTACAO_DO_NATURAIS = {"Do",  "Re",  "Mi", "Fa", "Sol", "La", "Si"};


    public static <T> T find(Collection<T> coll, Predicado<T> p) {
        T res = null;
        for (T obj : coll) {
            if (p.apply(obj)) {
                res = obj;
                break;
            }
        }
        return res;
    }



    public interface Predicado<T> {
        boolean apply(T t);
    }


    public static <T> Collection<T> findAll(Collection<T> coll, Predicado<T> p) {
        ArrayList<T> l = new ArrayList<>(coll.size());
        for (T obj : coll) {
            if (p.apply(obj)) {
                l.add(obj);
            }
        }
        return l;
    }


    //Obtem o indice circular com suporte a idx negativo
    public static int getIdxCircular(int idx, int length) {
        int r = idx % length;
        return r < 0 ? r + length : r;
    }

    public static String listToString(List l) {
        return l != null ? Arrays.toString(l.toArray()) : "null";
    }

    public static <T> T getRandomElement(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static <T> T getRandomElement(T[] array) {
        return array[new Random().nextInt(array.length)];
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz) {
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }


    public static void lancarExcecao(String s) {
        //como no servidor é perigoso lancar excecao, só lanço se for debug, em prod apenas logo
        if (play.Environment.simple().isDev()) {
            throw new RuntimeException(s);
        }
        else{
            Util.log("lancarExcecao() s: " + s);
        }
    }

    //forma a string randomica
    public static String gerarStrRandom(List<String> charsPossiveis, int tamStr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamStr; i++) {
            String nextChar = Util.getRandomElement(charsPossiveis);
            sb.append(nextChar);
        }

        return sb.toString();
    }
    public static int randomInt(int min, int max) {
        return getRandom().nextInt((max - min) + 1) + min;
    }
    private static Random r;

    public static Random getRandom() {
        if (r == null) {
            r = new Random();
        }
        return r;
    }

}
