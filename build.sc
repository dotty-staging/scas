// build.sc
import mill._, scalalib._, publish._

object scas extends ScalaModule with PublishModule {
  def scalaVersion = "3.0.0-RC1-bin-20201202-67d9790-NIGHTLY"
  def scalacOptions = Seq("-language:implicitConversions")
  def ivyDeps = Agg(
    ivy"org.scala-lang:scala-library:2.13.4"
  )
  object application extends ScalaModule {
    def scalaVersion = "3.0.0-RC1-bin-20201202-67d9790-NIGHTLY"
    def scalacOptions = Seq("-language:implicitConversions")
    def moduleDeps = Seq(scas)
    def ivyDeps = Agg(
      ivy"org.scala-lang::scala3-compiler:${scalaVersion()}",
      ivy"de.uni-mannheim.rz.krum:jas:2.6.6017",
      ivy"org.apache.logging.log4j:log4j-core:2.13.3",
      ivy"org.apache.logging.log4j:log4j-api:2.13.3",
      ivy"cc.redberry:rings:2.5.7",
      ivy"org.apache.commons:commons-math3:3.6.1",
      ivy"net.sourceforge.jscl-meditor:rendering:1.0"
    )
    def test(args: String*) = run(args: _*)
  }
  def publishVersion = "3.0"

  def pomSettings = PomSettings(
    description = "Scala Algebra System",
    organization = "com.github.rjolly",
    url = "https://github.com/rjolly/scas",
    licenses = Seq(License.`LGPL-2.0+`),
    versionControl = VersionControl.github("rjolly", "scas"),
    developers = Seq(
      Developer("rjolly", "Raphael Jolly", "https://github.com/rjolly")
    )
  )

  override def docJar = T {
    val outDir = T.dest
    val javadocDir = outDir / 'javadoc
    os.makeDir.all(javadocDir)
    eval.Result.Success(modules.Jvm.createJar(Agg(javadocDir))(outDir))
  }
}
