package controllers.authentication;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import controllers.Auth;
import controllers.routes;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import services.util.Util;

import static controllers.actionservices.LoginService.USUARIO_SESSAO_KEY;

public class AuthenticationAction extends Action.Simple {

    @Override
    public CompletionStage<Result> call(Context ctx) {
        Util.log("chamando AuthenticationAction");
        String usuarioLogado = ctx.session().get(USUARIO_SESSAO_KEY);
        Util.log("usuarioLogado: " + usuarioLogado);

        if (usuarioLogado != null) {
            return delegate.call(ctx);
        } else {
            CompletionStage<Result> promiseOfResult = CompletableFuture.supplyAsync(new Supplier<Result>() {
                public Result get() {
                    //return redirect(controllers.routes.Top.index());
                    //return redirect(controllers.routes.)
                    return ok("USUARIO NAO LOGADO");
                }
            });
            return promiseOfResult;
        }
    }
}
