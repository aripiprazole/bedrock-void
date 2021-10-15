package com.gabrielleeg1.bedrockvoid.protocol.serialization

import protocol.serialization.DecodingStrategy
import protocol.serialization.EncodingStrategy

interface Encoder<T> : EncodingStrategy<T>, DecodingStrategy<T>
