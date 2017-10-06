package services;

//Usa inteiros para representar error codes para não retornar strings e alguém descobrir a api só enviando erros
public class ErrorCodes {
    //Para ServicesController
    public static final int ERROR_CODE_BODY_EMPTY =5000 ;
    public static final int ERROR_CODE_METODO_NAO_ENCONTRADO = 5001;         //A string de validação embutida no id está em um formato inválido para o login
    public static final int ERROR_CODE_SV_NAO_ENCONTRADO_COOKIE=5008 ;      //A string de valdação da requisição possui uma sintaxe invalida
    public static final int ERROR_CODE_SV_REQUEST_SINTAXE_INVALIDA =5007 ;      //A string de valdação da requisição possui uma sintaxe invalida

    //Para login
    public static final int ERROR_CODE_CAMPO_ID_NAO_ENCOTRANDO = 5002;
    public static final int ERROR_CODE_STRING_VALIDACAO_LOGIN_ID_INVALIDO = 5003;         //A string de validação embutida no id está em um formato inválido para o login
    public static final int ERROR_CODE_USUARIO_INEXISTENTE = 5004;         //Não encontrou o usuario solicitado no banco

    //Pos login
    public static final int ERROR_CODE_SESSION_SEM_USUARIO_POS_LOGIN = 5009;
    public static final int ERROR_CODE_USUARIO_INEXISTENTE_POS_LOGIN = 5010;

    //Falhas no método encode() e decode()
    public static final int ERROR_CODE_CRYPTO_MANAGER_DECODE_EXCEPTION = 5005;
    public static final int ERROR_CODE_CRYPTO_MANAGER_ENCODE_EXCEPTION = 5006;

}
