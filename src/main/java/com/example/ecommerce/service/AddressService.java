package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.repository.UserRepository;

@Service
public class AddressService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	public Address createAddress(Address address) {
		validateUser(address.getUser().getUserId());
		return addressRepository.save(address);
	}

	public List<Address> getAllAddresses() {
		return addressRepository.findAll();
	}

	public Address getAddressById(Long addressId) {
		return addressRepository.findById(addressId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
	}

	public Address updateAddress(Long addressId, Address updatedAddress) {
		Address existing = getAddressById(addressId);

		existing.setArea(updatedAddress.getArea());
		existing.setCity(updatedAddress.getCity());
		existing.setState(updatedAddress.getState());
		existing.setPin(updatedAddress.getPin());

		if (updatedAddress.getUser() != null && updatedAddress.getUser().getUserId() != null) {
			User user = validateUser(updatedAddress.getUser().getUserId());
			existing.setUser(user);
		}

		try {
			return addressRepository.save(existing);
		} catch (DataIntegrityViolationException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update address");
		}
	}

	public Address patchAddress(Long id, Address patchData) {
		Address existing = getAddressById(id);

		if (patchData.getArea() != null)
			existing.setArea(patchData.getArea());
		if (patchData.getCity() != null)
			existing.setCity(patchData.getCity());
		if (patchData.getState() != null)
			existing.setState(patchData.getState());
		if (patchData.getPin() != null)
			existing.setPin(patchData.getPin());
		if (patchData.getUser() != null && patchData.getUser().getUserId() != null) {
			User u = validateUser(patchData.getUser().getUserId());
			existing.setUser(u);
		}

		try {
			return addressRepository.save(existing);
		} catch (DataIntegrityViolationException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to patch address");
		}
	}

	public void deleteAddress(Long addressId) {
		Address existing = getAddressById(addressId);
		addressRepository.delete(existing);
	}

	private User validateUser(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
}
