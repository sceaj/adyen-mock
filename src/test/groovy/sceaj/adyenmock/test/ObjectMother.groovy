package sceaj.adyenmock.test

import sceaj.adyenmock.api.v1.model.Card

class ObjectMother {

  static buildCard(props = null) {
    applyProperties(props, new Card(
      externalKey: UUID.randomUUID(),
      '4444-5555-6666-8291',
      'John Doe',
      '6',
      '2026',
      '372'
    ))
  }

  static <T> T applyProperties(props, T object) {
    if (props) {
        props.each { k, v -> object[k] = v }
    }
    object
  }
}
