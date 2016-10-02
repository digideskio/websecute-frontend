package models

/**
 * A generic page used in pagination.
 *
 * @param items Sequence of items on page.
 * @param page Page number.
 * @param offset Page offset.
 * @param total Total number of records.
 * @tparam A Record type.
 */
case class Page[A](
  items: Seq[A],
  page: Int,
  offset: Long,
  total: Long
) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}