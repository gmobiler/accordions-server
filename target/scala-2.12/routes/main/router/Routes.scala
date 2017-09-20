
// @GENERATOR:play-routes-compiler
// @SOURCE:G:/Projetos/PlayFramework/accordions-server/conf/routes
// @DATE:Wed Sep 20 14:05:10 GMT-03:00 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  Musicas_3: controllers.Musicas,
  // @LINE:21
  HomeController_1: controllers.HomeController,
  // @LINE:23
  CountController_0: controllers.CountController,
  // @LINE:25
  AsyncController_2: controllers.AsyncController,
  // @LINE:28
  Assets_4: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    Musicas_3: controllers.Musicas,
    // @LINE:21
    HomeController_1: controllers.HomeController,
    // @LINE:23
    CountController_0: controllers.CountController,
    // @LINE:25
    AsyncController_2: controllers.AsyncController,
    // @LINE:28
    Assets_4: controllers.Assets
  ) = this(errorHandler, Musicas_3, HomeController_1, CountController_0, AsyncController_2, Assets_4, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, Musicas_3, HomeController_1, CountController_0, AsyncController_2, Assets_4, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """musicas/""" + "$" + """id<[^/]+>""", """controllers.Musicas.get(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """musicas/meta/""" + "$" + """id<[^/]+>""", """controllers.Musicas.meta(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """musicas/checkforupdates/""" + "$" + """versaoAtualizacaoBancoCliente<[^/]+>""", """controllers.Musicas.checkForUpdates(versaoAtualizacaoBancoCliente:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """musicas/tracks/""" + "$" + """id<[^/]+>""", """controllers.Musicas.tracks(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """musicas/valormoedas/""" + "$" + """id<[^/]+>""", """controllers.Musicas.valorMoedas(id:Long)"""),
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """count""", """controllers.CountController.count"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """message""", """controllers.AsyncController.message"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_Musicas_get0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_get0_invoker = createInvoker(
    Musicas_3.get(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Musicas",
      "get",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """musicas/""" + "$" + """id<[^/]+>""",
      """ Obtém toda a musica incluindo meta-info e tracks dado o id da musica""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_Musicas_meta1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/meta/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_meta1_invoker = createInvoker(
    Musicas_3.meta(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Musicas",
      "meta",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """musicas/meta/""" + "$" + """id<[^/]+>""",
      """ Obtém toda a musica incluindo meta-info e tracks dado o id da musica""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_Musicas_checkForUpdates2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/checkforupdates/"), DynamicPart("versaoAtualizacaoBancoCliente", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_checkForUpdates2_invoker = createInvoker(
    Musicas_3.checkForUpdates(fakeValue[Int]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Musicas",
      "checkForUpdates",
      Seq(classOf[Int]),
      "GET",
      this.prefix + """musicas/checkforupdates/""" + "$" + """versaoAtualizacaoBancoCliente<[^/]+>""",
      """ Executa o algoritmo da atualizacao descrito na pasta de resources. Recebe um inteiro indicando a versaoAtualizacaoBanco mais recente que o usuario tem baixado. Retorna uma lista de musicas contendo as novas musicas que foram adicionadas ao banco a partir da versao enviada pelo usuario""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_Musicas_tracks3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/tracks/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_tracks3_invoker = createInvoker(
    Musicas_3.tracks(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Musicas",
      "tracks",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """musicas/tracks/""" + "$" + """id<[^/]+>""",
      """ Obtém as tracks de uma musica dado o id da musica""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_Musicas_valorMoedas4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/valormoedas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_valorMoedas4_invoker = createInvoker(
    Musicas_3.valorMoedas(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Musicas",
      "valorMoedas",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """musicas/valormoedas/""" + "$" + """id<[^/]+>""",
      """ Obtém o numero de moedas para comprar uma musica dado o id da musica""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_HomeController_index5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index5_invoker = createInvoker(
    HomeController_1.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:23
  private[this] lazy val controllers_CountController_count6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("count")))
  )
  private[this] lazy val controllers_CountController_count6_invoker = createInvoker(
    CountController_0.count,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CountController",
      "count",
      Nil,
      "GET",
      this.prefix + """count""",
      """ An example controller showing how to use dependency injection""",
      Seq()
    )
  )

  // @LINE:25
  private[this] lazy val controllers_AsyncController_message7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("message")))
  )
  private[this] lazy val controllers_AsyncController_message7_invoker = createInvoker(
    AsyncController_2.message,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AsyncController",
      "message",
      Nil,
      "GET",
      this.prefix + """message""",
      """ An example controller showing how to write asynchronous code""",
      Seq()
    )
  )

  // @LINE:28
  private[this] lazy val controllers_Assets_versioned8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned8_invoker = createInvoker(
    Assets_4.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_Musicas_get0_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_Musicas_get0_invoker.call(Musicas_3.get(id))
      }
  
    // @LINE:9
    case controllers_Musicas_meta1_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_Musicas_meta1_invoker.call(Musicas_3.meta(id))
      }
  
    // @LINE:12
    case controllers_Musicas_checkForUpdates2_route(params) =>
      call(params.fromPath[Int]("versaoAtualizacaoBancoCliente", None)) { (versaoAtualizacaoBancoCliente) =>
        controllers_Musicas_checkForUpdates2_invoker.call(Musicas_3.checkForUpdates(versaoAtualizacaoBancoCliente))
      }
  
    // @LINE:15
    case controllers_Musicas_tracks3_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_Musicas_tracks3_invoker.call(Musicas_3.tracks(id))
      }
  
    // @LINE:18
    case controllers_Musicas_valorMoedas4_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_Musicas_valorMoedas4_invoker.call(Musicas_3.valorMoedas(id))
      }
  
    // @LINE:21
    case controllers_HomeController_index5_route(params) =>
      call { 
        controllers_HomeController_index5_invoker.call(HomeController_1.index)
      }
  
    // @LINE:23
    case controllers_CountController_count6_route(params) =>
      call { 
        controllers_CountController_count6_invoker.call(CountController_0.count)
      }
  
    // @LINE:25
    case controllers_AsyncController_message7_route(params) =>
      call { 
        controllers_AsyncController_message7_invoker.call(AsyncController_2.message)
      }
  
    // @LINE:28
    case controllers_Assets_versioned8_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned8_invoker.call(Assets_4.versioned(path, file))
      }
  }
}
