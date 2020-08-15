package models

import play.api.data.Form
import play.api.data.Forms._

object CarHandler {
  case class Number(letters: String, digits: String, region: Int) {
    override def toString: String = {
      val letterTuple = letters.splitAt(1)
      letterTuple._1 + digits + letterTuple._2 + region.toString
    }
  }

  case class Car(number: Number, brand: String, model: String, color: String, year: Int)
  case class CarFormData(number: String, brand: String, model: String, color: String, year: Int)
  case class CarSet(cars: List[Car]) {
    def add(car: Car): CarSet = {
      CarSet(cars :+ car)
    }

    def delete(number: String): CarSet = {
      CarSet(this.cars.filterNot(c => c.number.toString == number))
    }

    def listAll(): CarSet = this
  }

  object NumberBuilder {
    def buildNumber(numberRaw: String): Number = {
      val letters = numberRaw.splitAt(1)._1 + numberRaw.splitAt(4)._2.splitAt(2)._1
      val numbers = numberRaw.splitAt(1)._2.splitAt(3)._1
      val region = numberRaw.splitAt(6)._2.toInt
      Number(letters, numbers, region)
    }
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
}

