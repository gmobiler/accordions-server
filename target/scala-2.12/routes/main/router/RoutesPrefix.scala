
// @GENERATOR:play-routes-compiler
// @SOURCE:G:/Projetos/PlayFramework/accordions-server/conf/routes
// @DATE:Wed Sep 20 14:05:10 GMT-03:00 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
