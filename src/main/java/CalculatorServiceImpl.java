import com.google.protobuf.Timestamp;
import com.trrp.trrp3server.*;
import io.grpc.stub.StreamObserver;

import java.util.Date;

public class CalculatorServiceImpl extends CalculatorGrpc.CalculatorImplBase {
    @Override
    public void calculate(CalculateMessage request, StreamObserver<CalculatedMessage> responseObserver) {
        CalculatedMessage.Builder calculatedBuilder = CalculatedMessage.newBuilder();
        Date calculationStart = new Date();

        double op1 = request.getOperand1();
        double op2 = request.getOperand2();

        switch (Operation.valueOf(request.getOperation())) {
            case DIV:
                if (0 == op1) {
                    calculatedBuilder.setCalculationStatus(CalculationStatus.FAILED.name());
                } else {
                    calculatedBuilder.setResult(op1 / op2);
                }
                break;
            case MUL:
                calculatedBuilder.setResult(op1 * op2);
                break;
            case SUB:
                calculatedBuilder.setResult(op1 - op2);
                break;
            case SUM:
                calculatedBuilder.setResult(op1 + op2);
                break;
        }

        calculatedBuilder.setCalculationTime((new Date().getTime() - calculationStart.getTime()) / 1000.0);
        calculatedBuilder.setCalculationDate(getCurrentTimestamp());

        responseObserver.onNext(calculatedBuilder.build());
        responseObserver.onCompleted();
    }

    private Timestamp getCurrentTimestamp() {
        long currentTime = new Date().getTime();
        long seconds = currentTime / 1000;
        long nanos = currentTime % 1000;
        return Timestamp.newBuilder().setSeconds(seconds).setNanos((int) nanos).build();
    }
}
