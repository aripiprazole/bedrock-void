package com.gabrielleeg1.bedrockvoid.protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import protocol.serialization.DecodingStrategy
import protocol.serialization.EncodingStrategy

interface PacketEncoder<T> :
  EncodingStrategy<T>,
  DecodingStrategy<T> where T : OutboundPacket, T : InboundPacket
