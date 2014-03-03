package com.example.server;

import java.util.List;

public interface AsyncResponse {

	void processFinish(List<Data> output);
}
