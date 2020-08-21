package models

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{ExecutionContext, Future}


object CarHandler {
  case class Number(letters: String, digits: String, region: Int) {
    override def toString: String = {
      val letterTuple = letters.splitAt(1)
      letterTuple._1 + digits + letterTuple._2 + region.toString
    }
  }

  object NumberBuilder {
    def buildNumber(numberRaw: String): Number = {
      val letters = numberRaw.splitAt(1)._1 + numberRaw.splitAt(4)._2.splitAt(2)._1
      val numbers = numberRaw.splitAt(1)._2.splitAt(3)._1
      val region = numberRaw.splitAt(6)._2.toInt
      Number(letters, numbers, region)
    }
  }

  case class Car(id: Int, number: String, brand: String, model: String, color: String, year: Int, added: String)
  case class CarFormData(number: String, brand: String, model: String, color: String, year: Int)

  class CarFormer(tag: Tag) extends Table[Car](tag, "car") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def number = column[String]("number")
    def brand = column[String]("brand")
    def model = column[String]("model")
    def color = column[String]("color")
    def year = column[Int]("year")
    def added = column[String]("added")

    override def * = (id, number, brand, model, color, year, added) <> (Car.tupled, Car.unapply)
  }

  object CarForm {
    val form = Form(
      mapping(
        "number" -> nonEmptyText,
        "brand" -> nonEmptyText,
        "model" -> nonEmptyText,
        "color" -> nonEmptyText,
        "year" -> number
      )(CarFormData.apply)(CarFormData.unapply)
    )
  }

  class Cars @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

    val cars = TableQuery[CarFormer]

    def add(car: Car): Future[String] = {
      dbConfig.db.run(cars += car).map(res => "Car added").recover {
        case ex: Exception => ex.getCause.getMessage
      }
    }

    def delete(number: String): Future[Int] = {
      dbConfig.db.run(cars.filter(_.number === number).delete)
    }

    def get(number: String): Future[Option[Car]] = {
      dbConfig.db.run(cars.filter(_.number === number).result.headOption)
    }

    def listAll: Future[Seq[Car]] = {
      dbConfig.db.run(cars.result)
    }
  }
}

