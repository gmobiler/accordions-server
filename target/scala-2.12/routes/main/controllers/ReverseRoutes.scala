
// @GENERATOR:play-routes-compiler
// @SOURCE:G:/Projetos/PlayFramework/accordions-server/conf/routes
// @DATE:Wed Oct 04 18:37:11 GMT-03:00 2017

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:41
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:41
    def versioned(file:Asset): Call = {
      implicit val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public")))
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:9
  class ReverseAuth(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def loginSecure(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "loginsecure")
    }
  
    // @LINE:11
    def obterMusiquinhas(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "obtermusiquinhas")
    }
  
    // @LINE:14
    def logout(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "logout")
    }
  
    // @LINE:13
    def login(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "login")
    }
  
  }

  // @LINE:6
  class ReverseServicesController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def services(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "services")
    }
  
  }

  // @LINE:36
  class ReverseCountController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:36
    def count(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "count")
    }
  
  }

  // @LINE:19
  class ReverseMusicas(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:31
    def valorMoedas(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/valormoedas/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:22
    def meta(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/meta/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:19
    def get(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:28
    def tracks(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/tracks/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:25
    def checkForUpdates(versaoAtualizacaoBancoCliente:Int): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/checkforupdates/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Int]].unbind("versaoAtualizacaoBancoCliente", versaoAtualizacaoBancoCliente)))
    }
  
  }

  // @LINE:16
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:34
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:16
    def testCookie(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "testcookie")
    }
  
  }

  // @LINE:38
  class ReverseAsyncController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:38
    def message(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "message")
    }
  
  }


}
