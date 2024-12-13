% Open the file
filename = 'Day13/Day13Input.txt'; % Replace with your actual file name
fileID = fopen(filename, 'r');

% Initialize matrices
A = [];
b = [];

% Loop through each line of the file
while ~feof(fileID)
    line = fgetl(fileID); % Read a line from the file
    
    if contains(line, 'Button A') % Look for Button A
        nums = sscanf(line, 'Button A: X+%d, Y+%d');
        rowA = nums'; % Extract and transpose to get [X, Y]
    elseif contains(line, 'Button B') % Look for Button B
        nums = sscanf(line, 'Button B: X+%d, Y+%d');
        rowB = nums'; % Extract and transpose to get [X, Y]
    elseif contains(line, 'Prize') % Look for Prize
        nums = sscanf(line, 'Prize: X=%d, Y=%d');
        b = [b; nums']; % Append the Prize values to b
        A = [A; rowA; rowB]; % Append the rows to A
    end
end

% Close the file
fclose(fileID);
X = [1,0;0,1];
% Reshape A into groups of 2x2 matrices (optional if multiple cases exist)
A = reshape(A', 2, 2, []); % Each slice along the 3rd dimension is a 2x2 matrix
b = reshape(b', 2, []);    % Each row corresponds to one case

% Part One
total = 0;
for i=1:length(A)
    x = A(:, :, i) \ b(:, i);
    temp = (x(1) * 3) + x(2);
    if (abs(temp-round(temp)) < 1e-10)
        total = total + round(temp);
    end
end

fprintf("Part One: %d\n", total);

% Part Two
total = 0;
for i=1:length(A)
    x = A(:, :, i) \ (b(:, i) + 10000000000000);
    temp = (x(1) * 3) + x(2);
    % here 1e-3 is the trick, inspired by the subreddit
    if (abs(temp-round(temp)) < 1e-3)
        total = total + round(temp);
    end
end

fprintf("Part Two: %d\n", total);

