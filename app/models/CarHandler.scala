package models

import play.api.data.Form
import play.api.data.Forms._

object CarHandler {
  case class Number(letters: String, digits: String, region: Int) {
    override def toString: String = {
      val letterTuple = letters.splitAt(2)
      letterTuple._1 + digits + letterTuple._2 + region.toString
    }
  }

  case class Car(number: Number, brand: String, model: String, color: String, year: Int)
  case class CarForm(number: String, brand: String, model: String, color: String, year: Int)
  case class CarSet(cars: List[Car]) {
    def add(car: Car): CarSet = {
      CarSet(cars :+ car)
    }

    def delete(number: String): CarSet = {
      CarSet(this.cars.filterNot(c => c.number.toString == number))
    }

    def listAll(): CarSet = this
  }

  object CarForm {
    val form = Form(
      mapping(
        "number" -> nonEmptyText,
        "brand" -> nonEmptyText,
        "model" -> nonEmptyText,
        "color" -> nonEmptyText,
        "year" -> number
      )(CarForm.apply)(CarForm.unapply)
    )
  }
}

