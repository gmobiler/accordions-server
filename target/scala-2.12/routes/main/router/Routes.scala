
// @GENERATOR:play-routes-compiler
// @SOURCE:G:/Projetos/PlayFramework/accordions-server/conf/routes
// @DATE:Wed Oct 04 18:37:11 GMT-03:00 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  ServicesController_3: controllers.ServicesController,
  // @LINE:9
  Auth_4: controllers.Auth,
  // @LINE:16
  HomeController_1: controllers.HomeController,
  // @LINE:19
  Musicas_5: controllers.Musicas,
  // @LINE:36
  CountController_0: controllers.CountController,
  // @LINE:38
  AsyncController_2: controllers.AsyncController,
  // @LINE:41
  Assets_6: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    ServicesController_3: controllers.ServicesController,
    // @LINE:9
    Auth_4: controllers.Auth,
    // @LINE:16
    HomeController_1: controllers.HomeController,
    // @LINE:19
    Musicas_5: controllers.Musicas,
    // @LINE:36
    CountController_0: controllers.CountController,
    // @LINE:38
    AsyncController_2: controllers.AsyncController,
    // @LINE:41
    Assets_6: controllers.Assets
  ) = this(errorHandler, ServicesController_3, Auth_4, HomeController_1, Musicas_5, CountController_0, AsyncController_2, Assets_6, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, ServicesController_3, Auth_4, HomeController_1, Musicas_5, CountController_0, AsyncController_2, Assets_6, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """services""", """controllers.ServicesController.services"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """loginsecure""", """controllers.Auth.loginSecure"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """obtermusiquinhas""", """controllers.Auth.obterMusiquinhas"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.Auth.login"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """logout""", """controllers.Auth.logout"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """testcookie""", """controllers.HomeController.testCookie"""),
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
  private[this] lazy val controllers_ServicesController_services0_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("services")))
  )
  private[this] lazy val controllers_ServicesController_services0_invoker = createInvoker(
    ServicesController_3.services,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ServicesController",
      "services",
      Nil,
      "POST",
      this.prefix + """services""",
      """Métodos de service""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_Auth_loginSecure1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("loginsecure")))
  )
  private[this] lazy val controllers_Auth_loginSecure1_invoker = createInvoker(
    Auth_4.loginSecure,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Auth",
      "loginSecure",
      Nil,
      "POST",
      this.prefix + """loginsecure""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_Auth_obterMusiquinhas2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("obtermusiquinhas")))
  )
  private[this] lazy val controllers_Auth_obterMusiquinhas2_invoker = createInvoker(
    Auth_4.obterMusiquinhas,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Auth",
      "obterMusiquinhas",
      Nil,
      "GET",
      this.prefix + """obtermusiquinhas""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_Auth_login3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_Auth_login3_invoker = createInvoker(
    Auth_4.login,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Auth",
      "login",
      Nil,
      "POST",
      this.prefix + """login""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_Auth_logout4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("logout")))
  )
  private[this] lazy val controllers_Auth_logout4_invoker = createInvoker(
    Auth_4.logout,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Auth",
      "logout",
      Nil,
      "GET",
      this.prefix + """logout""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_HomeController_testCookie5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("testcookie")))
  )
  private[this] lazy val controllers_HomeController_testCookie5_invoker = createInvoker(
    HomeController_1.testCookie,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "testCookie",
      Nil,
      "GET",
      this.prefix + """testcookie""",
      """""",
      Seq()
    )
  )

  // @LINE:19
  private[this] lazy val controllers_Musicas_get6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_get6_invoker = createInvoker(
    Musicas_5.get(fakeValue[Long]),
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

  // @LINE:22
  private[this] lazy val controllers_Musicas_meta7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/meta/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_meta7_invoker = createInvoker(
    Musicas_5.meta(fakeValue[Long]),
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

  // @LINE:25
  private[this] lazy val controllers_Musicas_checkForUpdates8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/checkforupdates/"), DynamicPart("versaoAtualizacaoBancoCliente", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_checkForUpdates8_invoker = createInvoker(
    Musicas_5.checkForUpdates(fakeValue[Int]),
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

  // @LINE:28
  private[this] lazy val controllers_Musicas_tracks9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/tracks/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_tracks9_invoker = createInvoker(
    Musicas_5.tracks(fakeValue[Long]),
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

  // @LINE:31
  private[this] lazy val controllers_Musicas_valorMoedas10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("musicas/valormoedas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Musicas_valorMoedas10_invoker = createInvoker(
    Musicas_5.valorMoedas(fakeValue[Long]),
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

  // @LINE:34
  private[this] lazy val controllers_HomeController_index11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index11_invoker = createInvoker(
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

  // @LINE:36
  private[this] lazy val controllers_CountController_count12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("count")))
  )
  private[this] lazy val controllers_CountController_count12_invoker = createInvoker(
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

  // @LINE:38
  private[this] lazy val controllers_AsyncController_message13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("message")))
  )
  private[this] lazy val controllers_AsyncController_message13_invoker = createInvoker(
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

  // @LINE:41
  private[this] lazy val controllers_Assets_versioned14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned14_invoker = createInvoker(
    Assets_6.versioned(fakeValue[String], fakeValue[Asset]),
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
    case controllers_ServicesController_services0_route(params) =>
      call { 
        controllers_ServicesController_services0_invoker.call(ServicesController_3.services)
      }
  
    // @LINE:9
    case controllers_Auth_loginSecure1_route(params) =>
      call { 
        controllers_Auth_loginSecure1_invoker.call(Auth_4.loginSecure)
      }
  
    // @LINE:11
    case controllers_Auth_obterMusiquinhas2_route(params) =>
      call { 
        controllers_Auth_obterMusiquinhas2_invoker.call(Auth_4.obterMusiquinhas)
      }
  
    // @LINE:13
    case controllers_Auth_login3_route(params) =>
      call { 
        controllers_Auth_login3_invoker.call(Auth_4.login)
      }
  
    // @LINE:14
    case controllers_Auth_logout4_route(params) =>
      call { 
        controllers_Auth_logout4_invoker.call(Auth_4.logout)
      }
  
    // @LINE:16
    case controllers_HomeController_testCookie5_route(params) =>
      call { 
        controllers_HomeController_testCookie5_invoker.call(HomeController_1.testCookie)
      }
  
    // @LINE:19
    case controllers_Musicas_get6_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_Musicas_get6_invoker.call(Musicas_5.get(id))
      }
  
    // @LINE:22
    case controllers_Musicas_meta7_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_Musicas_meta7_invoker.call(Musicas_5.meta(id))
      }
  
    // @LINE:25
    case controllers_Musicas_checkForUpdates8_route(params) =>
      call(params.fromPath[Int]("versaoAtualizacaoBancoCliente", None)) { (versaoAtualizacaoBancoCliente) =>
        controllers_Musicas_checkForUpdates8_invoker.call(Musicas_5.checkForUpdates(versaoAtualizacaoBancoCliente))
      }
  
    // @LINE:28
    case controllers_Musicas_tracks9_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_Musicas_tracks9_invoker.call(Musicas_5.tracks(id))
      }
  
    // @LINE:31
    case controllers_Musicas_valorMoedas10_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_Musicas_valorMoedas10_invoker.call(Musicas_5.valorMoedas(id))
      }
  
    // @LINE:34
    case controllers_HomeController_index11_route(params) =>
      call { 
        controllers_HomeController_index11_invoker.call(HomeController_1.index)
      }
  
    // @LINE:36
    case controllers_CountController_count12_route(params) =>
      call { 
        controllers_CountController_count12_invoker.call(CountController_0.count)
      }
  
    // @LINE:38
    case controllers_AsyncController_message13_route(params) =>
      call { 
        controllers_AsyncController_message13_invoker.call(AsyncController_2.message)
      }
  
    // @LINE:41
    case controllers_Assets_versioned14_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned14_invoker.call(Assets_6.versioned(path, file))
      }
  }
}
