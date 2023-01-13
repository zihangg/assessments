// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

contract Contract {
    mapping(string => uint256) private balances;
    mapping(string => string) private keys;


    function send(string memory _sender, string memory _recipient, uint256 _amount) public {
        require(balances[_sender] >= _amount, "Insufficient funds");
        balances[_sender] -= _amount;
        balances[_recipient] += _amount;
    }

    function saveKey(string memory _account, string memory _key) public {
        // key save = account created -> fund account
        keys[_account] = _key;
        balances[_account] = 1000000;
    }

    function getBalance(string memory _account) public view returns (uint256) {
        return balances[_account];
    }

    function getKeys(string memory _account) public view returns (string memory) {
        return keys[_account];
    }
}