package com.sanshengshui.iot.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.List;

/**
 * 协议初始化解码器.
 *
 * 用来判定实际使用什么协议.</b>
 *
 */
public class SocketChooseHandle extends ByteToMessageDecoder {
    /** 默认暗号长度为23 */
    private static final int MAX_LENGTH = 23;
    /** WebSocket握手的协议前缀 */
    private static final String WEBSOCKET_PREFIX = "GET /";


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String protocol = getBufStart(in);
        if (protocol.startsWith(WEBSOCKET_PREFIX)) {
            websocketAdd(ctx);

            ctx.pipeline().remove(LengthFieldBasedFrameDecoder.class);
            ctx.pipeline().remove(LengthFieldPrepender.class);
            //ctx.pipeline().remove(BytebufToByteHandle.class);
        }

        in.resetReaderIndex();
        ctx.pipeline().remove(this.getClass());

    }

    private String getBufStart(ByteBuf in){
        int length = in.readableBytes();
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }

        // 标记读位置
        in.markReaderIndex();
        byte[] content = new byte[length];
        in.readBytes(content);
        return new String(content);
    }
    public  void websocketAdd(ChannelHandlerContext ctx){

        // HttpServerCodec：将请求和应答消息解码为HTTP消息
        ctx.pipeline().addBefore("byteToBuf","http-codec",new HttpServerCodec());

        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
        ctx.pipeline().addBefore("byteToBuf","aggregator",new HttpObjectAggregator(65535));

        // ChunkedWriteHandler：向客户端发送HTML5文件
        ctx.pipeline().addBefore("byteToBuf","http-chunked",new ChunkedWriteHandler());

        ctx.pipeline().addBefore("byteToBuf","WebSocketAggregator",new WebSocketFrameAggregator(65535));

        // 在管道中添加我们自己的接收数据实现方法
        //ctx.pipeline().addBefore("byteToBuf","ws-handShake",wsHandShakeServerHandle);

        // 后续直接走消息处理
        //ctx.pipeline().addBefore("byteToBuf","wsPack",wsPacketHandle);

        // 编码。将通用byteBuf编码成binaryWebSocketFrame.通过前面的编码器
        //ctx.pipeline().addBefore("byteToBuf","bufToFrame",bytebufToBinaryFrameHandle);


    }
}

