package com.nus.assessment.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class ContractWrapper extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610af7806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80633a51d2461461005157806350bb9f3114610081578063bca8959b1461009d578063cd485c28146100cd575b600080fd5b61006b60048036038101906100669190610440565b6100e9565b60405161007891906104a2565b60405180910390f35b61009b600480360381019061009691906104bd565b610110565b005b6100b760048036038101906100b29190610440565b610166565b6040516100c491906105b4565b60405180910390f35b6100e760048036038101906100e29190610602565b610216565b005b600080826040516100fa91906106c9565b9081526020016040518091039020549050919050565b8060018360405161012191906106c9565b9081526020016040518091039020908161013b91906108ec565b50620f424060008360405161015091906106c9565b9081526020016040518091039020819055505050565b606060018260405161017891906106c9565b908152602001604051809103902080546101919061070f565b80601f01602080910402602001604051908101604052809291908181526020018280546101bd9061070f565b801561020a5780601f106101df5761010080835404028352916020019161020a565b820191906000526020600020905b8154815290600101906020018083116101ed57829003601f168201915b50505050509050919050565b8060008460405161022791906106c9565b9081526020016040518091039020541015610277576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161026e90610a0a565b60405180910390fd5b8060008460405161028891906106c9565b908152602001604051809103902060008282546102a59190610a59565b92505081905550806000836040516102bd91906106c9565b908152602001604051809103902060008282546102da9190610a8d565b92505081905550505050565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61034d82610304565b810181811067ffffffffffffffff8211171561036c5761036b610315565b5b80604052505050565b600061037f6102e6565b905061038b8282610344565b919050565b600067ffffffffffffffff8211156103ab576103aa610315565b5b6103b482610304565b9050602081019050919050565b82818337600083830152505050565b60006103e36103de84610390565b610375565b9050828152602081018484840111156103ff576103fe6102ff565b5b61040a8482856103c1565b509392505050565b600082601f830112610427576104266102fa565b5b81356104378482602086016103d0565b91505092915050565b600060208284031215610456576104556102f0565b5b600082013567ffffffffffffffff811115610474576104736102f5565b5b61048084828501610412565b91505092915050565b6000819050919050565b61049c81610489565b82525050565b60006020820190506104b76000830184610493565b92915050565b600080604083850312156104d4576104d36102f0565b5b600083013567ffffffffffffffff8111156104f2576104f16102f5565b5b6104fe85828601610412565b925050602083013567ffffffffffffffff81111561051f5761051e6102f5565b5b61052b85828601610412565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561056f578082015181840152602081019050610554565b60008484015250505050565b600061058682610535565b6105908185610540565b93506105a0818560208601610551565b6105a981610304565b840191505092915050565b600060208201905081810360008301526105ce818461057b565b905092915050565b6105df81610489565b81146105ea57600080fd5b50565b6000813590506105fc816105d6565b92915050565b60008060006060848603121561061b5761061a6102f0565b5b600084013567ffffffffffffffff811115610639576106386102f5565b5b61064586828701610412565b935050602084013567ffffffffffffffff811115610666576106656102f5565b5b61067286828701610412565b9250506040610683868287016105ed565b9150509250925092565b600081905092915050565b60006106a382610535565b6106ad818561068d565b93506106bd818560208601610551565b80840191505092915050565b60006106d58284610698565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061072757607f821691505b60208210810361073a576107396106e0565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026107a27fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610765565b6107ac8683610765565b95508019841693508086168417925050509392505050565b6000819050919050565b60006107e96107e46107df84610489565b6107c4565b610489565b9050919050565b6000819050919050565b610803836107ce565b61081761080f826107f0565b848454610772565b825550505050565b600090565b61082c61081f565b6108378184846107fa565b505050565b5b8181101561085b57610850600082610824565b60018101905061083d565b5050565b601f8211156108a05761087181610740565b61087a84610755565b81016020851015610889578190505b61089d61089585610755565b83018261083c565b50505b505050565b600082821c905092915050565b60006108c3600019846008026108a5565b1980831691505092915050565b60006108dc83836108b2565b9150826002028217905092915050565b6108f582610535565b67ffffffffffffffff81111561090e5761090d610315565b5b610918825461070f565b61092382828561085f565b600060209050601f8311600181146109565760008415610944578287015190505b61094e85826108d0565b8655506109b6565b601f19841661096486610740565b60005b8281101561098c57848901518255600182019150602085019450602081019050610967565b868310156109a957848901516109a5601f8916826108b2565b8355505b6001600288020188555050505b505050505050565b7f496e73756666696369656e742066756e64730000000000000000000000000000600082015250565b60006109f4601283610540565b91506109ff826109be565b602082019050919050565b60006020820190508181036000830152610a23816109e7565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610a6482610489565b9150610a6f83610489565b9250828203905081811115610a8757610a86610a2a565b5b92915050565b6000610a9882610489565b9150610aa383610489565b9250828201905080821115610abb57610aba610a2a565b5b9291505056fea2646970667358221220a4507ee03b883944d4a472ba80a12cc0c0437547c3d67651a5b099bda9117cbd64736f6c63430008110033";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETKEYS = "getKeys";

    public static final String FUNC_SAVEKEY = "saveKey";

    public static final String FUNC_SEND = "send";

    @Deprecated
    protected ContractWrapper(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractWrapper(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ContractWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ContractWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> getBalance(String _account) {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getKeys(String _account) {
        final Function function = new Function(FUNC_GETKEYS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> saveKey(String _account, String _key) {
        final Function function = new Function(
                FUNC_SAVEKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_account), 
                new org.web3j.abi.datatypes.Utf8String(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> send(String _sender, String _recipient, BigInteger _amount) {
        final Function function = new Function(
                FUNC_SEND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_sender), 
                new org.web3j.abi.datatypes.Utf8String(_recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ContractWrapper load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractWrapper(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ContractWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractWrapper(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ContractWrapper load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ContractWrapper(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ContractWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ContractWrapper(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ContractWrapper> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ContractWrapper.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ContractWrapper> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractWrapper.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ContractWrapper> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ContractWrapper.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ContractWrapper> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractWrapper.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
