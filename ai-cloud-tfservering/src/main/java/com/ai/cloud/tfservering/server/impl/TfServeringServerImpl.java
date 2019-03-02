package com.ai.cloud.tfservering.server.impl;

import com.ai.cloud.tfservering.server.TfServeringServer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict;
import tensorflow.serving.PredictionServiceGrpc;

import java.util.Arrays;
import java.util.List;

public class TfServeringServerImpl implements TfServeringServer {

    @Override
    public void getPredictResponse() {
        List<Float> floatList = Arrays.asList(1.0f,2.0f,0.5f);
        ManagedChannel channel = ManagedChannelBuilder.forAddress("0.0.0.0", 9000).usePlaintext(true).build();
        //这里还是先用block模式
        PredictionServiceGrpc.PredictionServiceBlockingStub stub = PredictionServiceGrpc.newBlockingStub(channel);
        //创建请求
        Predict.PredictRequest.Builder predictRequestBuilder = Predict.PredictRequest.newBuilder();
        //模型名称和模型方法名预设
        Model.ModelSpec.Builder modelSpecBuilder = Model.ModelSpec.newBuilder();
        modelSpecBuilder.setName("example_model");
        modelSpecBuilder.setSignatureName("prediction");
        predictRequestBuilder.setModelSpec(modelSpecBuilder);
        //设置入参,访问默认是最新版本，如果需要特定版本可以使用tensorProtoBuilder.setVersionNumber方法
        TensorProto.Builder tensorProtoBuilder = TensorProto.newBuilder();
        tensorProtoBuilder.setDtype(DataType.DT_FLOAT);
        TensorShapeProto.Builder tensorShapeBuilder = TensorShapeProto.newBuilder();

        tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1));
        tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(3));
        tensorProtoBuilder.setTensorShape(tensorShapeBuilder.build());
        tensorProtoBuilder.addAllFloatVal(floatList);
        predictRequestBuilder.putInputs("input", tensorProtoBuilder.build());
        //访问并获取结果
        Predict.PredictResponse predictResponse = stub.predict(predictRequestBuilder.build());
        org.tensorflow.framework.TensorProto result=predictResponse.toBuilder().getOutputsOrThrow("output");
        System.out.println("预测值是:"+result.getFloatValList());
    }

}
